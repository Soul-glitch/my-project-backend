package com.example;

import com.example.entity.dto.Account;
import com.example.entity.dto.User;
import com.example.service.AccountService;
import com.example.utils.InsertSort;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MyProjectBackendApplicationTests {

    @Resource
    AccountService service;
    @Resource
    InsertSort insertSort;

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
            user.setDate(account.getRegister_time());
            user.setUsername(account.getUsername());
            users.add(user);
        });
        System.out.println(users);
    }

    @Test
    void insert(){
        int[] arr = {8,10,21,4,11,56,33};
        insertSort.insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
