package vip.linhs.stock.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TickerConfigDao;

@Repository
public class TickerConfigDaoImpl extends BaseDao implements TickerConfigDao {

    @Override
    public List<Map<String, Object>> getValuesByKey(String key) {
        String sql = "select c.id, c.`key`, c.`value`, c.state, r.webhook FROM ticker_config c"
                + " left join robot r on c.robot_id = r.id and r.mark_for_delete = false"
                + " where c.`key` = ? and c.mark_for_delete = false";
        return jdbcTemplate.queryForList(sql, key);
    }

}
