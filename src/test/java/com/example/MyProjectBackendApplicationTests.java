package com.example;

import com.example.entity.dto.Account;
import com.example.entity.dto.User;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MyProjectBackendApplicationTests {

    @Resource
    AccountService service;

    @Test
    void contextLoads() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    void data(){
        List<Account> accounts = service.list();
        System.out.println(accounts);
        List<User> users = new ArrayList<>();
        accounts.forEach(account -> {
            User user = new User();
            user.setId(account.getId());
            user.setRole(account.getRole());
            user.setEmail(account.getEmail());
            user.setDate(account.getRegisterTime());
            user.setUsername(account.getUsername());
            users.add(user);
        });
        System.out.println(users);
    }
}
