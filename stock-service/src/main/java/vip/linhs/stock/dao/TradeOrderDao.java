package vip.linhs.stock.dao;

import java.util.Date;
import java.util.List;

import vip.linhs.stock.model.po.TradeOrder;

public interface TradeOrderDao {

    void save(TradeOrder tradeOrder);

    List<TradeOrder> getAll();

    List<TradeOrder> getListByDate(Date date);

    void delete(String tradeCode, String tradeType);

}
