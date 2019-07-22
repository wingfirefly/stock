package vip.linhs.stock.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.DailyIndexDao;
import vip.linhs.stock.model.po.DailyIndex;

@Repository
public class DailyIndexDaoImpl extends BaseDao implements DailyIndexDao {

    private static final String INSERT_SQL = "insert into daily_index(stock_info_id, date, opening_price, pre_closing_price, highest_price, closing_price, lowest_price, trading_volume, trading_value) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public int save(DailyIndex dailyIndex) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(DailyIndexDaoImpl.INSERT_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            DailyIndexDaoImpl.setArgument(ps, dailyIndex);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void save(List<DailyIndex> list) {
        jdbcTemplate.batchUpdate(DailyIndexDaoImpl.INSERT_SQL, list, list.size(),
                DailyIndexDaoImpl::setArgument);
    }

    private static void setArgument(PreparedStatement ps, DailyIndex dailyIndex)
            throws SQLException {
        ps.setInt(1, dailyIndex.getStockInfoId());
        StatementCreatorUtils.setParameterValue(ps, 2, SqlTypeValue.TYPE_UNKNOWN,
                dailyIndex.getDate());
        ps.setBigDecimal(3, dailyIndex.getOpeningPrice());
        ps.setBigDecimal(4, dailyIndex.getPreClosingPrice());
        ps.setBigDecimal(5, dailyIndex.getHighestPrice());
        ps.setBigDecimal(6, dailyIndex.getClosingPrice());
        ps.setBigDecimal(7, dailyIndex.getLowestPrice());
        ps.setLong(8, dailyIndex.getTradingVolume());
        ps.setBigDecimal(9, dailyIndex.getTradingValue());
    }

    @Override
    public DailyIndex getDailyIndexByFullCodeAndDate(String fullCode, Date date) {
        List<DailyIndex> list = jdbcTemplate.query(
                "select d.id, d.stock_info_id, d.date from daily_index d where stock_info_id = (select id from stock_info s where concat(s.exchange, s.code) = ?) and d.date = ? limit 0, 1",
                new Object[] { fullCode, new java.sql.Date(date.getTime()) }, new BeanPropertyRowMapper<>(DailyIndex.class));
        return list.isEmpty() ? null : list.get(0);
    }

}
