package vip.linhs.stock.service;

import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.po.TradeUser;

public interface TradeService {

    TradeMethod getTradeMethodByName(String name);

    TradeUser getTradeById(int id);

    TradeRule getTradeRuleByStockCode(String stockCode);

    void update(TradeUser tradeUser);

}
