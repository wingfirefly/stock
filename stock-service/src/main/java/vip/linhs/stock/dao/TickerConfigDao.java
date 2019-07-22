package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.TickerConfig;

public interface TickerConfigDao {

    List<TickerConfig> getListByKey(String key);

}
