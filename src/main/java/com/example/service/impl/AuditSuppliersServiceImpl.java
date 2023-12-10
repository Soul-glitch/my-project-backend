package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.AuditSuppliers;
import com.example.mapper.AuditSuppliersMapper;
import com.example.service.AuditSuppliersService;
import org.springframework.stereotype.Service;

@Service
public class AuditSuppliersServiceImpl extends ServiceImpl<AuditSuppliersMapper, AuditSuppliers> implements AuditSuppliersService {
}
