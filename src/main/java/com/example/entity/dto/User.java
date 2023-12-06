package com.example.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    Integer id;
    String username;
    String email;
    Date date;
    String role;
}
