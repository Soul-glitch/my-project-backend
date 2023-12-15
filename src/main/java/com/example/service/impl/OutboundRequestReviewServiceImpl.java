package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.OutboundRequestReview;
import com.example.mapper.OutboundRequestReviewMapper;
import com.example.service.OutboundRequestReviewService;
import org.springframework.stereotype.Service;

@Service
public class OutboundRequestReviewServiceImpl extends ServiceImpl<OutboundRequestReviewMapper, OutboundRequestReview> implements OutboundRequestReviewService {
}
