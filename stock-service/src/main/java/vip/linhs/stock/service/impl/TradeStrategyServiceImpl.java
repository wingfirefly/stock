package vip.linhs.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.TradeStrategyDao;
import vip.linhs.stock.model.po.TradeStrategy;
import vip.linhs.stock.service.TradeStrategyService;
import vip.linhs.stock.util.StockConsts;

@Service
public class TradeStrategyServiceImpl implements TradeStrategyService {

    @Autowired
    private TradeStrategyDao tradeStrategyDao;

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_STRATEGY)
    @Override
    public List<TradeStrategy> getAll() {
        return tradeStrategyDao.getAll();
    }

}
