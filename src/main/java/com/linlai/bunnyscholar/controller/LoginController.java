package com.linlai.bunnyscholar.controller;


import com.linlai.bunnyscholar.entity.User;
import com.linlai.bunnyscholar.pojo.CurrentUser;
import com.linlai.bunnyscholar.pojo.LoginUser;
import com.linlai.bunnyscholar.pojo.Result;
import com.linlai.bunnyscholar.service.IUserService;
import com.linlai.bunnyscholar.service.LoginService;
import com.linlai.bunnyscholar.utils.CommonUtil;
import com.linlai.bunnyscholar.utils.JwtUtil;
import com.linlai.bunnyscholar.utils.RedisUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @ApiOperation("发送验证码")
    @PostMapping("/sendVerificationCode")
    public Result sendVerificationCode(@RequestParam String email) {
        String random = loginService.sendVerificationCode(email);
        return Result.success(random);
    }


    /**
     * 根据邮箱和验证码进行登录并且返回token
     *
     * @param email
     * @param code
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestParam String email,
                        @RequestParam String code) {
        String token = loginService.login(email, code);
        return Result.success(token);
    }
}
