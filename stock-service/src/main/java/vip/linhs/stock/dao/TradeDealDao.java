package vip.linhs.stock.dao;

import java.util.Date;
import java.util.List;

import vip.linhs.stock.model.po.TradeDeal;

public interface TradeDealDao {

    void add(TradeDeal tradeDeal);

    List<TradeDeal> getByDate(Date date);

}
