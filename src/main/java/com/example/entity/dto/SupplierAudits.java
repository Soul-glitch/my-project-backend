package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("supplier_audits")
public class SupplierAudits {
    @TableId(type = IdType.AUTO)
    Integer number;
    String supplier_name;
    String abbreviation;
    String company_address;
    String contact;
    String contact_number;
    String applicant;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8",locale="zh")
    Date application_time;
    String application_status;
    String reviewer;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8",locale="zh")
    Date review_time;
    String review_status;
}
