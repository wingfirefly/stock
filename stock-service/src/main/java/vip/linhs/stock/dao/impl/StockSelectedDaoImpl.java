package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.StockSelectedDao;
import vip.linhs.stock.model.po.StockSelected;

@Repository
public class StockSelectedDaoImpl extends BaseDao implements StockSelectedDao {

    private static final String SQL_SELECT_BASE_COLUMNS = "select id as id, code as code, rate as rate, create_time as createTime, update_time as updateTime, description as description from stock_selected where 1 = 1";

    @Override
    public List<StockSelected> getList() {
        List<StockSelected> list = jdbcTemplate.query(SQL_SELECT_BASE_COLUMNS,
                BeanPropertyRowMapper.newInstance(StockSelected.class));
        return list;
    }

}
