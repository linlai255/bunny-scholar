package com.linlai.bunnyscholar.interceptor;

import com.linlai.bunnyscholar.config.RedisPrefixConfig;
import com.linlai.bunnyscholar.entity.User;
import com.linlai.bunnyscholar.pojo.CurrentUser;
import com.linlai.bunnyscholar.pojo.LoginUser;
import com.linlai.bunnyscholar.service.IUserService;
import com.linlai.bunnyscholar.utils.DateUtil;
import com.linlai.bunnyscholar.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class RecordSubmitInterceptor implements HandlerInterceptor {
    private RedisUtil redisUtil;

    private RedisPrefixConfig redisPrefixConfig;

    public RecordSubmitInterceptor(RedisUtil redisUtil, RedisPrefixConfig redisPrefixConfig) {
        this.redisUtil = redisUtil;
        this.redisPrefixConfig = redisPrefixConfig;
    }

    @Value("${app.max.times}")
    private Integer maxTimes;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginUser loginUser = CurrentUser.get();
        Object o = redisUtil.get(redisPrefixConfig.recordTimesLockPrefix + loginUser.getId());
        Assert.isNull(o, "您有尚未完成的降重请求，请耐心等待");
        if ( !this.checkBeforeSubmit(loginUser)) {
            throw new IllegalArgumentException("每天最多降重" + maxTimes + "次");
        }
        redisUtil.set(redisPrefixConfig.recordTimesLockPrefix + loginUser.getId(), 1, 60*60);
        return true;
    }

    private boolean checkBeforeSubmit(LoginUser user) {
        String key = redisPrefixConfig.recordTimesPrefix + redisPrefixConfig.split + user.getId() + redisPrefixConfig.split + DateUtil.getNowDate();
        Object o = redisUtil.get(key);
        if (o == null) {
            redisUtil.set(key, 1, 24*60*60);
            return true;
        }
        Integer times = (Integer) o;
        if (times >= maxTimes) {
            return false;
        }
        redisUtil.set(key, times + 1, 24*60*60);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LoginUser loginUser = CurrentUser.get();
        redisUtil.del(redisPrefixConfig.recordTimesLockPrefix + loginUser.getId());
        return;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        return;
    }
}
