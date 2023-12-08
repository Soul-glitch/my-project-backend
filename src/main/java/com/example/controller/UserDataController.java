package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.RestBean;
import com.example.entity.dto.Account;
import com.example.entity.dto.User;
import com.example.mapper.AccountMapper;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/api/user")
public class UserDataController {
    @Resource
    AccountService service;
    @Resource
    PasswordEncoder encoder;

    /**
     * 查询全部用户的数据
     *
     * @return
     */
    @GetMapping("/data")
    public RestBean<List<User>> userData() {
        List<Account> accounts = service.list();
        System.out.println(accounts);
        List<User> users = new ArrayList<>();
        return getListRestBean(accounts, users);
    }

    /**
     * 根据条件查询用户信息
     * @param username
     * @param email
     * @return
     */
    @GetMapping("/condition")
    public RestBean<List<User>> conditionData(@RequestParam String username,@RequestParam String email) {
        if(Objects.equals(username, null)&&Objects.equals(email, null)){
            return this.userData();
        }
        List<Account> accounts;
        List<User> users = new ArrayList<>();
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.like("username",username).or().like("email",email);
        accounts = service.list(wrapper);
        return getListRestBean(accounts, users);
    }
    /**
     * 根据条件删除用户信息
     * @param user
     * @return
     */
    @PostMapping("/delete")
    public RestBean<String> deleteData(@RequestBody User user) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("id",user.getId());
        service.remove(wrapper);
        log.info("删除数据成功");
        return RestBean.success("删除数据成功");
    }

    /**
     * 添加用户信息
     * @param account
     * @return
     */
    @PostMapping("/adduser")
    public RestBean<String> addUser(@RequestBody Account account){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(account.getPassword());
        List<Account> accounts = service.list();
        for (Account account1 : accounts) {
            if(account1.getEmail().equals(account.getEmail())){
                return RestBean.forbidden("此电子邮件已被注册");
            }
            if(account1.getUsername().equals(account.getUsername())){
                return RestBean.forbidden("用户名称已被他人占用");
            }
        }
        account.setPassword(password);
        boolean save = service.save(account);
        if(save){
            log.info("添加用户信息成功");
            return RestBean.success("添加用户信息成功");
        }else {
            return RestBean.forbidden("内部错误");
        }

    }

    @PostMapping("updateUser")
    public RestBean<String> updateUser(@RequestBody User user){
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Account::getId,user.getId());
        Account account = new Account();
        account.setUsername(user.getUsername());
        account.setEmail(user.getEmail());
        account.setRole(user.getRole());
        account.setRegisterTime(service.getOne(wrapper).getRegisterTime());
        account.setPassword(service.getOne(wrapper).getPassword());
        account.setId(service.getOne(wrapper).getId());
        if(service.update(account,wrapper)){
            return RestBean.success("数据更新成功");
        }
        return RestBean.forbidden("数据异常");
    }

    /**
     * dto
     * @param accounts
     * @param users
     * @return
     */
    private RestBean<List<User>> getListRestBean(List<Account> accounts, List<User> users) {
        accounts.forEach(account -> {
            User user = new User();
            user.setId(account.getId());
            user.setRole(account.getRole());
            user.setEmail(account.getEmail());
            user.setDate(account.getRegisterTime());
            user.setUsername(account.getUsername());
            users.add(user);
        });
        return RestBean.success(users);
    }


}
