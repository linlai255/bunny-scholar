package com.linlai.bunnyscholar.service.impl;

import com.linlai.bunnyscholar.config.RedisPrefixConfig;
import com.linlai.bunnyscholar.entity.User;
import com.linlai.bunnyscholar.pojo.Result;
import com.linlai.bunnyscholar.service.IUserService;
import com.linlai.bunnyscholar.service.LoginService;
import com.linlai.bunnyscholar.utils.CommonUtil;
import com.linlai.bunnyscholar.utils.JwtUtil;
import com.linlai.bunnyscholar.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserService userService;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private RedisPrefixConfig redisPrefixConfig;

    private static final String YZM = "yzm";
    @Override
    public String sendVerificationCode(String email) {
        String emailKey = redisPrefixConfig.emailPrefix + email;
        Object emailExist = redisUtil.get(emailKey);
        Assert.isNull(emailExist, "当前验证码尚在有效期内");
        redisUtil.set(emailKey, "1", 60);
        String random = CommonUtil.getRandom6();
        String key = YZM + email;
        redisUtil.set(key, random, 60);
        Object o = redisUtil.get(key);
        log.info("obj: {}", o);
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(email);
        //邮件标题
        message.setSubject("登录验证码");
        //邮件内容
        message.setText("您正在尝试登录CLL语音采集系统，您的验证码为: " + random);
        log.info("start send mail param: {}", message);
        mailSender.send(message);
        return random;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String login(String email, String code) {
        Object o = redisUtil.get(YZM + email);
        Assert.notNull(o, "验证码已失效");
        Assert.isTrue(o.toString().equals(code), "验证码错误");
        User byEmail = userService.getByEmail(email);
        if (byEmail == null) {
            byEmail = new User();
            byEmail.setEmail(email);
            userService.save(byEmail);
        }
        String s = JwtUtil.generateJwtToken(byEmail.getId());
        return s;
    }
}
