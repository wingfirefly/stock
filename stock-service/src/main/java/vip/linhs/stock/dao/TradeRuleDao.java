package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;

public interface TradeRuleDao {

    PageVo<TradeRule> get(PageParam pageParam);

    void updateState(int state, int id);

}
