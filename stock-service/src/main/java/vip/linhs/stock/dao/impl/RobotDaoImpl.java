package vip.linhs.stock.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.RobotDao;

@Repository
public class RobotDaoImpl extends BaseDao implements RobotDao {

    @Override
    public List<Map<String, Object>> getByType(int type) {
        String sql = "select id, type, webhook, state from robot where mark_for_delete = false and type = ?";
        return jdbcTemplate.queryForList(sql, type);
    }

}
