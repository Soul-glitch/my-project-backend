package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.RestBean;
import com.example.entity.dto.Account;
import com.example.entity.dto.User;
import com.example.mapper.AccountMapper;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserDataController {
    @Resource
    AccountService service;
    @Resource
    AccountMapper mapper;

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
        if(Objects.equals(username, "")&&Objects.equals(email, "")){
            return this.userData();
        }
        List<Account> accounts;
        List<User> users = new ArrayList<>();
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.like("username",username).or().like("email",email);
        accounts = mapper.selectList(wrapper);
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
        mapper.delete(wrapper);
        return RestBean.success("删除数据成功");
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
