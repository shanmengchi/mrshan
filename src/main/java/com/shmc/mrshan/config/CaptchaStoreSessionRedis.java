package com.shmc.mrshan.config;

import com.baomidou.kisso.captcha.ICaptchaStore;
import com.shmc.mrshan.utils.BeanUtils;
import com.shmc.mrshan.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaptchaStoreSessionRedis implements ICaptchaStore {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    BeanUtils beanUtils;
//    public static CaptchaStoreSessionRedis captchaStoreSessionRedis ;
//    @PostConstruct
//    public void init() {
//        captchaStoreSessionRedis = this;
//        captchaStoreSessionRedis.redisUtils = this.redisUtils;
//    }
    @Override
    public String get(String s) {
        return BeanUtils.getBean(RedisUtils.class).get(s).toString();
    }

    @Override
    public boolean put(String s, String s1) {
        return BeanUtils.getBean(RedisUtils.class).set(s,s1,10);
    }
}
