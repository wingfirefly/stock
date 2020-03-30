package vip.linhs.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    public List<Map<String, String>> getAll() {
        return stringRedisTemplate.keys(StockConsts.CACHE_KEY_PREFIX + "*").stream().map(key -> {
            String value = get(key);
            HashMap<String, String> map = new HashMap<>();
            map.put("key", key);
            map.put("value", value);
            return map;
        }).collect(Collectors.toList());
    }

}
