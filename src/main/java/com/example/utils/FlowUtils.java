package com.example.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 限流工具
 * 六十秒内不能再次请求验证码
 */
@Component
public class FlowUtils {

    @Resource
    StringRedisTemplate template;

    //是否检查通过
    public boolean limitOnceCheck(String key,int blockTime){
        //是否在一个冷却的状态
        if(Boolean.TRUE.equals(template.hasKey(key))){
            //在冷却时间中不能发送请求
            return false;
        }else {
            //不在冷却时间中就丢尽冷却时间
            template.opsForValue().set(key,"",blockTime, TimeUnit.SECONDS);
            return true;
        }
    }
}
