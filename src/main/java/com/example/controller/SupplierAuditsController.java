package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.RestBean;
import com.example.entity.dto.AuditSuppliers;
import com.example.entity.dto.SupplierAudits;
import com.example.service.AuditSuppliersService;
import com.example.service.SupplierAuditsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/suppliers")
public class SupplierAuditsController {
    @Resource
    SupplierAuditsService supplierAuditsService;
    @Resource
    AuditSuppliersService auditSuppliersService;

    /**
     * 查询数据
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/data")
    public RestBean<Page> data(@RequestParam int pageNum, @RequestParam int pageSize) {
        Page<SupplierAudits> pageInfo = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SupplierAudits> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SupplierAudits::getNumber);
        supplierAuditsService.page(pageInfo, wrapper);
        return RestBean.success(pageInfo);
    }

    @PostMapping("/condition")
    public RestBean<Page> condition(@RequestParam(required = false) int pageNum,
                                    @RequestParam(required = false) int pageSize,
                                    @RequestBody SupplierAudits supplierAudits) {
        log.info("要查询的数据是 : {}",supplierAudits);
        Page<SupplierAudits> pageInfo = new Page<>(pageNum, pageSize);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String Application_time = sdf.format(supplierAudits.getApplication_time());
//        String Review_time = sdf.format(supplierAudits.getReview_time());
        Date applicationTime = supplierAudits.getApplication_time();
        Date reviewTime = supplierAudits.getReview_time();
        LambdaQueryWrapper<SupplierAudits> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(SupplierAudits::getSupplier_name, supplierAudits.getSupplier_name())
                .like(SupplierAudits::getContact, supplierAudits.getContact())
                .like(SupplierAudits::getApplicant, supplierAudits.getApplicant())
                .like(SupplierAudits::getApplication_time, applicationTime)
                .like(SupplierAudits::getApplication_status, supplierAudits.getApplication_status())
                .like(SupplierAudits::getReviewer, supplierAudits.getReviewer())
                .like(SupplierAudits::getReview_time, reviewTime)
                .like(SupplierAudits::getReview_status, supplierAudits.getReview_status())
                .orderByAsc(SupplierAudits::getNumber);
        supplierAuditsService.page(pageInfo, wrapper);
        return RestBean.success(pageInfo);
    }

    @GetMapping("/audits")
    public RestBean<AuditSuppliers> audits(@RequestParam int number){
        LambdaQueryWrapper<AuditSuppliers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuditSuppliers::getNumber,number);
        AuditSuppliers one = auditSuppliersService.getOne(wrapper);
        return RestBean.success(one);
    }

    @PostMapping("/opinions")
    public RestBean<String> opinions(@RequestParam String text,@RequestBody AuditSuppliers auditSuppliers){
        LambdaQueryWrapper<AuditSuppliers> auditSuppliersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SupplierAudits> supplierAuditsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        supplierAuditsLambdaQueryWrapper.eq(SupplierAudits::getNumber,auditSuppliers.getNumber());
        SupplierAudits one = supplierAuditsService.getOne(supplierAuditsLambdaQueryWrapper);
        auditSuppliersLambdaQueryWrapper.eq(AuditSuppliers::getNumber,auditSuppliers.getNumber());
        if("success".equals(text)){
            one.setReview_status("通过");
        }else if("overrule".equals(text)){
            one.setReview_status("驳回");
        }else {
            return RestBean.forbidden("未收到修改状态");
        }
        boolean updateOne = auditSuppliersService.update(auditSuppliers, auditSuppliersLambdaQueryWrapper);
        boolean updateTwo = supplierAuditsService.update(one, supplierAuditsLambdaQueryWrapper);
        if(updateOne&&updateTwo){
            return RestBean.success("修改数据成功");
        }
        return RestBean.forbidden("服务内部错误");
    }
}
