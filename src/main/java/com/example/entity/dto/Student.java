package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("student")
@AllArgsConstructor
public class Student {
    String sno;
    String sname;
    String ssex;
    int sage;
}
