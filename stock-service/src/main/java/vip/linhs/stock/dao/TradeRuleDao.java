package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;

public interface TradeRuleDao {

    TradeRule getTradeRuleByStockCode(String stockCode);

    PageVo<TradeRule> get(PageParam pageParam);

}
