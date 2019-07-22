package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TickerConfigDao;
import vip.linhs.stock.model.po.TickerConfig;

@Repository
public class TickerConfigDaoImpl extends BaseDao implements TickerConfigDao {

    @Override
    public List<TickerConfig> getListByKey(String key) {
        String sql = "select c.id, c.`key`, c.`value`, c.robot_id as robotId, c.state from ticker_config c where c.`key` = ? and c.mark_for_delete = false";
        return jdbcTemplate.query(sql, new String[] { key }, BeanPropertyRowMapper.newInstance(TickerConfig.class));
    }

}
