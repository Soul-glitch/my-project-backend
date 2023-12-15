package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.RestBean;
import com.example.entity.dto.OutboundRequest;
import com.example.entity.dto.OutboundRequestReview;
import com.example.service.OutboundRequestReviewService;
import com.example.service.OutboundRequestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/outbound")
public class OutboundRequestReviewController {
    @Resource
    OutboundRequestService requestService;
    @Resource
    OutboundRequestReviewService requestReviewService;

    @GetMapping("/data")
    public RestBean<Page> data(@RequestParam int pageNum, @RequestParam int pageSize) {
        Page<OutboundRequestReview> pageInfo = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<OutboundRequestReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(OutboundRequestReview::getNumber);
        requestReviewService.page(pageInfo, wrapper);
        return RestBean.success(pageInfo);
    }

    @PostMapping("/condition")
    public RestBean<Page> condition(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody OutboundRequestReview requestReview) {
        if (requestReview == null) {
            return RestBean.failure(400, "数据为空或遭受拦截");
        }
        Page<OutboundRequestReview> pageInfo = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<OutboundRequestReview> wrapper = new LambdaQueryWrapper<>();
        log.info("要查询的数据是 {}", requestReview);
        wrapper.eq(OutboundRequestReview::getDocument_number, requestReview.getDocument_number())
                .eq(OutboundRequestReview::getBusiness_type, requestReview.getBusiness_type())
                .eq(OutboundRequestReview::getContract_type, requestReview.getContract_type())
                .eq(OutboundRequestReview::getApplicant, requestReview.getApplicant())
//                .eq(OutboundRequestReview::getApplication_time, requestReview.getApplication_time())
                .eq(OutboundRequestReview::getApplication_status, requestReview.getApplication_status())
                .eq(OutboundRequestReview::getReviewer, requestReview.getReviewer())
//                .eq(OutboundRequestReview::getReview_time, requestReview.getReview_time())
                .eq(OutboundRequestReview::getReview_status, requestReview.getReview_status());
        requestReviewService.page(pageInfo, wrapper);
        return RestBean.success(pageInfo);
    }

    @GetMapping("/review")
    public RestBean<OutboundRequest> review(@RequestParam int number){
        LambdaQueryWrapper<OutboundRequest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutboundRequest::getNumber,number);
        OutboundRequest one = requestService.getOne(wrapper);
        return RestBean.success(one);
    }

    @PostMapping("/opinions")
    public RestBean<String> opinions(@RequestParam String text,@RequestBody OutboundRequestReview outboundRequestReview){
        return null;
    }
}
