package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("outbound_request")
public class OutboundRequest {
    Integer number;
    String review_opinions;
}
