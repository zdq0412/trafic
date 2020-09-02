package com.jxqixin.trafic.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 设置值
     * @param key
     */
    public void set(String key,String value){
        ValueOperations  valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * 设置值，并设置过期时间
     * @param key
     * @param value
     * @param expire  过期时间，分钟
     */
    public void setExpire(String key,String value,long expire){
        ValueOperations  valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,expire, TimeUnit.MINUTES);
    }
    /**
     * 通过key获取值
     * @param key
     * @return
     */
    public Object get(String key){
        ValueOperations  valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
    /**
     * 根据key删除
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 产生token字符串
     * @return
     */
    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public void clearCache() {
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }
}
