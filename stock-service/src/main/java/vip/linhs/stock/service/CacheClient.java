package vip.linhs.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;

import vip.linhs.stock.model.vo.CacheVo;

@Component
public class CacheClient {

    @Autowired
    private CacheManager cacheManager;

    public List<CacheVo> getAll() {
        ArrayList<CacheVo> list = new ArrayList<>();
        for (String name : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache cache = cacheManager.getCache(name);
            Object nativeCache = cache.getNativeCache();
            if (nativeCache instanceof Cache) {
                @SuppressWarnings("unchecked")
                Cache<Object, Object> caffeineCache = (Cache<Object, Object>) nativeCache;
                list.addAll(getList(name, caffeineCache));
            }
        }
        return list;
    }

    private List<CacheVo> getList(String name, Cache<Object, Object> cache) {
        ConcurrentMap<Object, Object> cacheMap = cache.asMap();
        return cacheMap.entrySet().stream().map(e -> {
            CacheVo map = new CacheVo();
            map.setKey(e.getKey().toString());
            map.setName(name);
            map.setValue(JSON.toJSONString(e.getValue()));
            return map;
        }).collect(Collectors.toList());
    }

    public void remove(String name, String key) {
        org.springframework.cache.Cache cache = cacheManager.getCache(name);
        if (cache != null) {
            cache.evict(key);
        }
    }

}
