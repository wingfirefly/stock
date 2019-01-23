package vip.linhs.stock.dao;

import java.util.List;
import java.util.Map;

public interface TickerConfigDao {

    List<Map<String, Object>> getValuesByKey(String key);

}
