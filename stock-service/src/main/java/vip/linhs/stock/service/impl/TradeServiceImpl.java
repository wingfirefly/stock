package vip.linhs.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.TradeMethodDao;
import vip.linhs.stock.dao.TradeRuleDao;
import vip.linhs.stock.dao.TradeUserDao;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.StockConsts;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMethodDao tradeMethodDao;

    @Autowired
    private TradeUserDao tradeUserDao;

    @Autowired
    private TradeRuleDao tradeRuleDao;

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_METHOD, key = "#name")
    @Override
    public TradeMethod getTradeMethodByName(String name) {
        return tradeMethodDao.getByName(name);
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#id")
    @Override
    public TradeUser getTradeById(int id) {
        return tradeUserDao.getById(id);
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_RULE, key = "#stockCode")
    @Override
    public TradeRule getTradeRuleByStockCode(String stockCode) {
        return tradeRuleDao.getTradeRuleByStockCode(stockCode);
    }

    @CacheEvict(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#tradeUser.id")
    @Override
    public void update(TradeUser tradeUser) {
        tradeUserDao.update(tradeUser);
    }

}
