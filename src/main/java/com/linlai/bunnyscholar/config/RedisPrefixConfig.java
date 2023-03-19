package com.linlai.bunnyscholar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisPrefixConfig {


    @Value("${app.prefix.split}")
    public String split;
    @Value("${app.prefix.recordTimes}")
    public String recordTimesPrefix;

    @Value("${app.prefix.email}")
    public String emailPrefix;

    @Value("${app.prefix.recordTimes.lock}")
    public String recordTimesLockPrefix;


}
