package vip.linhs.stock.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import vip.linhs.stock.util.StockConsts;

@Component
public class RedisClient {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void put(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
    }

    public void put(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, StockConsts.DURATION_REDIS_DEFAULT, TimeUnit.MINUTES);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public String remove(String key) {
        String value = get(key);
        stringRedisTemplate.delete(key);
        return value;
    }

}
