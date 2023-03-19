package com.linlai.bunnyscholar.interceptor;

import com.linlai.bunnyscholar.entity.User;
import com.linlai.bunnyscholar.pojo.CurrentUser;
import com.linlai.bunnyscholar.pojo.LoginUser;
import com.linlai.bunnyscholar.service.IUserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import io.jsonwebtoken.Jwts;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private IUserService userService;

    @Value("${jwt.private.key}")
    private String jwtPrivateKey;

    public LoginInterceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request: {}", request);
        log.info("response: {}", response);
        String token = request.getHeader("token");
        try {
            Claims claims = Jwts.parser().setSigningKey( jwtPrivateKey ).parseClaimsJws( token ).getBody();
            String id = claims.getId();
            User user = userService.getById(Integer.valueOf(id));
            Assert.notNull(user, "请重新登录");
            LoginUser loginUser = new LoginUser();
            loginUser.setEmail(user.getEmail());
            loginUser.setName(user.getName());
            loginUser.setId(user.getId());
            loginUser.setToken(token);
            CurrentUser.set(loginUser);
        } catch (Exception e) {
            log.error("token parse fail: {}", e);
            throw new IllegalArgumentException("请重新登录");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        CurrentUser.remove();
        return;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        return;
    }
}
