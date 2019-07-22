package vip.linhs.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.TickerConfigDao;
import vip.linhs.stock.model.po.TickerConfig;
import vip.linhs.stock.service.TickerConfigService;
import vip.linhs.stock.util.StockConsts;

@Service
public class TickerConfigServiceImpl implements TickerConfigService {

    @Autowired
    private TickerConfigDao tickerConfigDao;

    @Cacheable(value = StockConsts.CACHE_KEY_CONFIG_TICKER, key = "#key")
    @Override
    public List<TickerConfig> getListByKey(String key) {
        return tickerConfigDao.getListByKey(key);
    }

}
