package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.TickerConfig;

public interface TickerConfigService {

    List<TickerConfig> getListByKey(String key);

}
