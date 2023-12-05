package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.vo.request.ConfirmResetVO;
import com.example.entity.vo.request.EmailRegisterVO;
import com.example.entity.vo.request.EmailResetVO;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;
import java.util.function.Function;
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
        return this.messageHandle(vo,service::registerEmailAccount);
    }

    /**
     * 验证码验证
     * @param vo
     * @return
     */
    @PostMapping("/reset-confirm")
    public RestBean<Void> restConfirm(@RequestBody @Valid ConfirmResetVO vo){
        return this.messageHandle(vo,service::resetConfirm);
    }

    @PostMapping("/reset-password")
    public RestBean<Void> restConfirm(@RequestBody @Valid EmailResetVO vo){
        return this.messageHandle(vo,service::resetEmailAccountPassword);
    }
    //统一返回方法
    private <T> RestBean<Void> messageHandle(T vo, Function<T,String> function){
        return messageHandle(()->function.apply(vo));
    }
    private RestBean<Void> messageHandle(Supplier<String> action){
        String message = action.get();
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }
}
