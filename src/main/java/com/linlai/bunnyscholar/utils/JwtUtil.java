package com.linlai.bunnyscholar.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JwtUtil {

    private static final String privateKey = "bunny-scholar-2023-0319";

    public static String generateJwtToken(Integer userId) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, 1);
        Date time = calendar1.getTime();
        time.setHours(0);
        time.setMinutes(0);

        JwtBuilder builder= Jwts.builder()
                .setId(userId.toString())   //设置唯一编号
                .setSubject("小白")//设置主题  可以是JSON数据
                .setIssuedAt(new Date())//设置签发日期
                .setExpiration(time)
                .signWith(SignatureAlgorithm.HS256,privateKey);
        return builder.compact();
    }

}
