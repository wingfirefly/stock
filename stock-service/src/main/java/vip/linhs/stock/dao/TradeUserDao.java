package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.TradeUser;

public interface TradeUserDao {

    TradeUser getById(int id);

    void update(TradeUser tradeUser);

}
