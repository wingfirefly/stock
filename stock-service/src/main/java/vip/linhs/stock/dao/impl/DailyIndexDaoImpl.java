package vip.linhs.stock.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.DailyIndexDao;
import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.vo.DailyIndexVo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.util.SqlCondition;

@Repository
public class DailyIndexDaoImpl extends BaseDao implements DailyIndexDao {

    private static final String INSERT_SQL = "insert into daily_index(code, date, opening_price, pre_closing_price, highest_price, closing_price, lowest_price, trading_volume, trading_value) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        ps.setString(1, dailyIndex.getCode());
        StatementCreatorUtils.setParameterValue(ps, 2, Types.DATE,
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
    public PageVo<DailyIndexVo> getDailyIndexList(PageParam pageParam) {
        String sql = "select"
            + " s.name, s.abbreviation, d.code, d.date, d.pre_closing_price as preClosingPrice,"
            + " d.closing_price as closingPrice, d.lowest_price as lowestPrice,"
            + " d.highest_price as highestPrice, d.opening_price as openingPrice,"
            + " d.trading_value as tradingValue, d.trading_volume as tradingVolume"
            + " from daily_index d, stock_info s where d.code = concat(s.exchange, s.code)";

        SqlCondition dataSqlCondition = new SqlCondition(sql, pageParam.getCondition());
        dataSqlCondition.addString("date", "date");

        int totalRecords = jdbcTemplate.queryForObject(dataSqlCondition.getCountSql(), Integer.class, dataSqlCondition.toArgs());

        dataSqlCondition.addSort("tradingValue", false, true);
        dataSqlCondition.addSql(" limit ?, ?");
        dataSqlCondition.addPage(pageParam.getStart(), pageParam.getLength());

        List<DailyIndexVo> list = jdbcTemplate.query(dataSqlCondition.toSql(),
                BeanPropertyRowMapper.newInstance(DailyIndexVo.class), dataSqlCondition.toArgs());
        return new PageVo<>(list, totalRecords);
    }

    @Override
    public List<DailyIndex> getDailyIndexListByDate(Date date) {
        String sql = "select"
            + " id, code, date, pre_closing_price as preClosingPrice,"
            + " closing_price as closingPrice, lowest_price as lowestPrice,"
            + " highest_price as highestPrice, opening_price as openingPrice,"
            + " trading_value as tradingValue, trading_volume as tradingVolume"
            + " from daily_index where date = ?";
        List<DailyIndex> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DailyIndex.class),
                new java.sql.Date(date.getTime()));
        return list;
    }

}
