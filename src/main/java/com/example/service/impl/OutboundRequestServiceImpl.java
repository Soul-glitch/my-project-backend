package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.OutboundRequest;
import com.example.mapper.OutboundRequestMapper;
import com.example.service.OutboundRequestService;
import org.springframework.stereotype.Service;

@Service
public class OutboundRequestServiceImpl extends ServiceImpl<OutboundRequestMapper, OutboundRequest> implements OutboundRequestService {
}
