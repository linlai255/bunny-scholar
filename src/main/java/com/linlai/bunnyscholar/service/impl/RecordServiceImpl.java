package com.linlai.bunnyscholar.service.impl;

import com.linlai.bunnyscholar.config.RedisPrefixConfig;
import com.linlai.bunnyscholar.pojo.LoginUser;
import com.linlai.bunnyscholar.service.IRecordService;
import com.linlai.bunnyscholar.entity.Record;
import com.linlai.bunnyscholar.mapper.RecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linlai.bunnyscholar.service.IUserService;
import com.linlai.bunnyscholar.utils.DateUtil;
import com.linlai.bunnyscholar.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linlai
 * @since 2023-03-18
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    private RedisUtil redisUtil;

    private RedisPrefixConfig redisPrefixConfig;

    public RecordServiceImpl(RedisUtil redisUtil, RedisPrefixConfig redisPrefixConfig) {
        this.redisUtil = redisUtil;
        this.redisPrefixConfig = redisPrefixConfig;
    }

    @Value("${app.max.times}")
    private Integer maxTimes;

    @Override
    public String submit(String content, LoginUser user) {
        //this.checkBeforeSubmit(user);
        Record record = new Record();
        record.setBeforeContent(content);
        record.setUserId(user.getId());
        this.save(record);
        String result = "降重结果";
        record.setAfterContent(result);
        this.updateById(record);
        return result;
    }

    private void checkBeforeSubmit(LoginUser user) {
        String key = redisPrefixConfig.recordTimesPrefix + redisPrefixConfig.split + user.getId() + redisPrefixConfig.split + DateUtil.getNowDate();
        Object o = redisUtil.get(key);
        if (o == null) {
            redisUtil.set(key, 1, 24*60*60);
            return;
        }
        Integer times = (Integer) o;
        Assert.isTrue(times < maxTimes, "每天最多降重" + maxTimes + "次");
        redisUtil.set(key, times + 1, 24*60*60);
    }
}
