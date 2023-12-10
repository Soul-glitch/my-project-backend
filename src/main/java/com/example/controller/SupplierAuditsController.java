package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.RestBean;
import com.example.entity.dto.SupplierAudits;
import com.example.service.AuditSuppliersService;
import com.example.service.SupplierAuditsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierAuditsController {
    @Resource
    SupplierAuditsService supplierAuditsService;
    @Resource
    AuditSuppliersService auditSuppliersService;

    /**
     * 查询数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/data")
    public RestBean<Page> data(@RequestParam int pageNum, @RequestParam int pageSize){
        Page<SupplierAudits> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<SupplierAudits> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SupplierAudits::getNumber);
        supplierAuditsService.page(pageInfo,wrapper);
        return RestBean.success(pageInfo);
    }
}
