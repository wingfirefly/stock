package vip.linhs.stock.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.StockLogDao;
import vip.linhs.stock.model.po.StockLog;

@Repository
public class StockLogDaoImpl extends BaseDao implements StockLogDao {

    @Override
    public void add(List<StockLog> list) {
        jdbcTemplate.batchUpdate(
                "insert into stock_log(stock_info_id, date, type, old_value, new_value) values(?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        StockLog stockLog = list.get(i);
                        ps.setInt(1, stockLog.getStockInfoId());
                        ps.setDate(2, new java.sql.Date(stockLog.getDate().getTime()));
                        ps.setInt(3, stockLog.getType());
                        ps.setString(4, stockLog.getOldValue());
                        ps.setString(5, stockLog.getNewValue());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }

    @Override
    public void setStockIdByCodeType(List<String> list, int type) {
        String whereCause = String.join(",",
                list.stream().map(str -> "?").collect(Collectors.toList()));
        String sql = "update stock_log l, stock_info s set l.stock_info_id = s.id where l.new_value = s.name and s.id > 1 and s.code in ("
                + whereCause + ") and l.type = ?";
        list.add(String.valueOf(type));
        jdbcTemplate.update(sql, list.toArray());
    }

}
