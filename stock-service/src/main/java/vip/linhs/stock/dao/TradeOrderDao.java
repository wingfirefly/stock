package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.TradeOrder;

public interface TradeOrderDao {

    void save(TradeOrder tradeOrder);

    List<TradeOrder> getAll();

}
