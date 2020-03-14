package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.TradeStockInfoRule;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;

public interface TradeStockInfoRuleDao {

    PageVo<TradeStockInfoRule> get(PageParam pageParam);

    void updateStateById(int state, int id);

}
