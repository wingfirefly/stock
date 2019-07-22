package vip.linhs.stock.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeMethodDao;
import vip.linhs.stock.model.po.TradeMethod;

@Repository
public class TradeMethodDaoImpl extends BaseDao implements TradeMethodDao {

    @Override
    public TradeMethod getByName(String name) {
        return jdbcTemplate.queryForObject("select id, name, url, state from trade_method where name = ?",
                new String[] { name }, BeanPropertyRowMapper.newInstance(TradeMethod.class));
    }

}
