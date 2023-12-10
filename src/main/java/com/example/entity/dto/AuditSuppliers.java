package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("audit_suppliers")
@AllArgsConstructor
public class AuditSuppliers {
    Integer number;
    String supplier_name;
    String review_opinions;
}
