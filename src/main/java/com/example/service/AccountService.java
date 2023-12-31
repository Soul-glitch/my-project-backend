package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Account;
import com.example.entity.vo.request.ConfirmResetVO;
import com.example.entity.vo.request.EmailRegisterVO;
import com.example.entity.vo.request.EmailResetVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmail(String text);
    //发送邮件
    String registerEmailVerifyCode(String type,String email,String ip);
    //注册
    String registerEmailAccount(EmailRegisterVO vo);
    //重置密码的验证码验证
    String resetConfirm(ConfirmResetVO vo);
    //重置密码
    String resetEmailAccountPassword(EmailResetVO vo);
}
