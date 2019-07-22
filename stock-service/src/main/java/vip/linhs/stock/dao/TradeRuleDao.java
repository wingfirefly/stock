package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.TradeRule;

public interface TradeRuleDao {

    TradeRule getTradeRuleByStockCode(String stockCode);

}
