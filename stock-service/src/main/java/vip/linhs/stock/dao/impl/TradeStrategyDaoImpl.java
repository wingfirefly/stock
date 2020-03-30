package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeStrategyDao;
import vip.linhs.stock.model.po.TradeStrategy;

@Repository
public class TradeStrategyDaoImpl extends BaseDao implements TradeStrategyDao {

    private static final String SQL_SELECT_BASE_COLUMNS = "select id as id, name as name, bean_name as beanName, state as state, create_time as createTime, update_time as updateTime from trade_strategy where 1 = 1";

    @Override
    public List<TradeStrategy> getAll() {
        return jdbcTemplate.query(SQL_SELECT_BASE_COLUMNS, BeanPropertyRowMapper.newInstance(TradeStrategy.class));
    }

}
