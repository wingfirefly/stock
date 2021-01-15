package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.StockLog;

public interface StockLogDao {

    void add(List<StockLog> list);

    void setStockIdByCodeType(List<String> list, int type);

}
