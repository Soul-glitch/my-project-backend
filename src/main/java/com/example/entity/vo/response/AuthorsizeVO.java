package com.example.entity.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorsizeVO {
    String username;
    String role;
    String token;
    Date expire;
}
