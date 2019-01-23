package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.StockInfo;

public interface StockDao {

    List<StockInfo> getAll();

    void add(List<StockInfo> list);

    void update(List<StockInfo> list);

}
