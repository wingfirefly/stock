package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.TradeUser;

public interface TradeUserDao {

    TradeUser getById(int id);

    void update(TradeUser tradeUser);

    List<TradeUser> getList();

}
