package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("outbound_request_review")
public class OutboundRequestReview {
    Integer number;
    String document_number;
    String associated_document_number;
    String business_type;
    String client;
    String contract_number;
    String contract_type;
    String applicant_type;
    String applicant;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8",locale="zh")
    Date application_time;
    String application_status;
    String reviewer;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8",locale="zh")
    Date review_time;
    String review_status;
}
