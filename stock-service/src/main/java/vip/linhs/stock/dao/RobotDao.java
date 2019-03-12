package vip.linhs.stock.dao;

import java.util.List;
import java.util.Map;

public interface RobotDao {

    List<Map<String, Object>> getByType(int type);

}
