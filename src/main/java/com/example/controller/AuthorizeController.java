package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.vo.request.EmailRegisterVO;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {
    @Resource
    AccountService service;

    /**
     * 验证码的发送
     * @param email
     * @param type
     * @param request
     * @return
     */
    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@RequestParam @Email String email, @RequestParam @Pattern(regexp = "(register|reset)") String type, HttpServletRequest request) {
        return this.messageHandle(()-> service.registerEmailVerifyCode(type, email, request.getRemoteAddr()));
    }

    /**
     * 注册接口
     * @param vo
     * @return
     */
    @PostMapping("/register")
    public RestBean<Void> register(@RequestBody @Valid EmailRegisterVO vo){
        return this.messageHandle(()->service.registerEmailAccount(vo));
    }
    //统一返回方法
    private RestBean<Void> messageHandle(Supplier<String> action){
        String message = action.get();
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }
}
