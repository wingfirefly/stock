package vip.linhs.stock.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.util.SqlCondition;

@Repository
public class StockInfoDaoImpl extends BaseDao implements vip.linhs.stock.dao.StockInfoDao {

    private static final String SELECT_SQL = "select id, code, name, exchange, abbreviation, state, create_time as createTime, update_time as updateTime from stock_info where mark_for_delete = false";

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

    @Override
    public void setStockIdByCodeType(List<String> list, int type) {
        String whereCause = String.join(",",
                list.stream().map(str -> "?").collect(Collectors.toList()));
        String sql = "update stock_log l, stock_info s set l.stock_info_id = s.id where l.new_value = s.name and s.id > 1 and s.code in ("
                + whereCause + ") and l.type = ?";
        list.add(String.valueOf(type));
        jdbcTemplate.update(sql, list.toArray());
    }

    @Override
    public PageVo<StockInfo> get(PageParam pageParam) {
        SqlCondition dataSqlCondition = new SqlCondition(
                StockInfoDaoImpl.SELECT_SQL,
                pageParam.getCondition());

        int totalRecords = jdbcTemplate.queryForObject(dataSqlCondition.getCountSql(),
                dataSqlCondition.toArgs(), Integer.class);

        dataSqlCondition.addSql(" limit ?, ?");
        dataSqlCondition.addPage(pageParam.getStart(), pageParam.getLength());

        List<StockInfo> list = jdbcTemplate.query(dataSqlCondition.toSql(),
                dataSqlCondition.toArgs(), BeanPropertyRowMapper.newInstance(StockInfo.class));
        return new PageVo<>(list, totalRecords);
    }

    @Override
    public StockInfo getStockByFullCode(String code) {
        return jdbcTemplate.queryForObject(StockInfoDaoImpl.SELECT_SQL + " and concat(exchange, code) = ?",
                new String[] { code }, BeanPropertyRowMapper.newInstance(StockInfo.class));
    }

}
