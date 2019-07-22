package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.TradeMethod;

public interface TradeMethodDao {

    TradeMethod getByName(String name);

}
