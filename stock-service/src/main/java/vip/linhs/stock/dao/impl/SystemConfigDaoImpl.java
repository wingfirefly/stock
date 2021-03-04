package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.SystemConfigDao;
import vip.linhs.stock.model.po.SystemConfig;
import vip.linhs.stock.util.StockConsts;

@Repository
public class SystemConfigDaoImpl extends BaseDao implements SystemConfigDao {

    private static final String SQL_SELECT_BASE_COLUMNS = "select id as id, name, value1, value2, value3, state, create_time as createTime, update_time as updateTime from system_config where 1 = 1";

    @Override
    public List<SystemConfig> getByName(String name) {
        return jdbcTemplate.query(SQL_SELECT_BASE_COLUMNS + " and name = ? and state = ?", new Object[] { name, StockConsts.TradeState.Valid.value() },
                BeanPropertyRowMapper.newInstance(SystemConfig.class));
    }

    @Override
    public List<SystemConfig> getAll() {
        return jdbcTemplate.query(SQL_SELECT_BASE_COLUMNS, BeanPropertyRowMapper.newInstance(SystemConfig.class));
    }

}
