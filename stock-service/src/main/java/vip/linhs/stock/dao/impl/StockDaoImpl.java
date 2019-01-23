package vip.linhs.stock.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.model.po.StockInfo;

@Repository
public class StockDaoImpl extends BaseDao implements vip.linhs.stock.dao.StockDao {

    @Override
    public List<StockInfo> getAll() {
        return jdbcTemplate.query(
                "select id, code, name, exchange, abbreviation, state from stock_info where mark_for_delete = false",
                new BeanPropertyRowMapper<>(StockInfo.class));
    }

    @Override
    public void add(List<StockInfo> list) {
        jdbcTemplate.batchUpdate(
                "insert into stock_info(code, name, exchange, abbreviation, state) values(?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        StockInfo stockInfo = list.get(i);
                        ps.setString(1, stockInfo.getCode());
                        ps.setString(2, stockInfo.getName());
                        ps.setString(3, stockInfo.getExchange());
                        ps.setString(4, stockInfo.getAbbreviation());
                        ps.setInt(5, stockInfo.getState());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }

    @Override
    public void update(List<StockInfo> list) {
        jdbcTemplate.batchUpdate(
                "update stock_info set code = ?, name = ?, exchange = ?, abbreviation = ? where mark_for_delete = false and id = ?",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        StockInfo stockInfo = list.get(i);
                        ps.setString(1, stockInfo.getCode());
                        ps.setString(2, stockInfo.getName());
                        ps.setString(3, stockInfo.getExchange());
                        ps.setString(4, stockInfo.getAbbreviation());
                        ps.setInt(5, stockInfo.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }

}
