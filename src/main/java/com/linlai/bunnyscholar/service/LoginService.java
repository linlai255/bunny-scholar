package com.linlai.bunnyscholar.service;

public interface LoginService {
    String sendVerificationCode(String email);

    String login(String email, String code);
}
