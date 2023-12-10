package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.SupplierAudits;
import com.example.mapper.SupplierAuditsMapper;
import com.example.service.SupplierAuditsService;
import org.springframework.stereotype.Service;

@Service
public class SupplierAuditsImpl extends ServiceImpl<SupplierAuditsMapper, SupplierAudits> implements SupplierAuditsService {
}
