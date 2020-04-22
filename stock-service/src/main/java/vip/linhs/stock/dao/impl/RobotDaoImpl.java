package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.RobotDao;
import vip.linhs.stock.model.po.Robot;

@Repository
public class RobotDaoImpl extends BaseDao implements RobotDao {

    private static final String SELECT_SQL = "select id, type, webhook, state from robot where 1 = 1";

    @Override
    public Robot getById(int id) {
        String sql = RobotDaoImpl.SELECT_SQL + " and id = ?";
        return jdbcTemplate.queryForObject(sql, new Integer[] { id }, BeanPropertyRowMapper.newInstance(Robot.class));
    }

    @Override
    public List<Robot> getListByType(int type) {
        String sql = RobotDaoImpl.SELECT_SQL + " and type = ?";
        return jdbcTemplate.query(sql, new Integer[] { type }, BeanPropertyRowMapper.newInstance(Robot.class));
    }

}
