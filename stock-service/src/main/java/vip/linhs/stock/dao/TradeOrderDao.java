package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.TradeOrder;

public interface TradeOrderDao {

    void add(TradeOrder tradeOrder);

    void update(TradeOrder tradeOrder);

    List<TradeOrder> getLastListByRuleId(int ruleId, int userId);

    void setInvalidByRuleId(int ruleId);

}
