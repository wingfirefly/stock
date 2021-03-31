package vip.linhs.stock.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeDealDao;
import vip.linhs.stock.model.po.TradeDeal;

@Repository
public class TradeDealDaoImpl extends BaseDao implements TradeDealDao {

    private static final String SELECT_SQL = "select id, stock_code as stockCode, deal_code as dealCode, price, volume, trade_type as tradeType, trade_time as tradeTime, create_time as createTime, update_time as updateTime from trade_deal where 1 = 1";

    @Override
    public void add(TradeDeal tradeDeal) {
        jdbcTemplate.update(
                "insert into trade_deal(stock_code, deal_code, price, volume, trade_type, trade_time, user_id) values(?, ?, ?, ?, ?, ?, ?)",
                tradeDeal.getStockCode(), tradeDeal.getDealCode(), tradeDeal.getPrice(), tradeDeal.getVolume(), tradeDeal.getTradeType(), tradeDeal.getTradeTime(), 1);
    }

    @Override
    public List<TradeDeal> getByDate(Date date) {
        return jdbcTemplate.query(TradeDealDaoImpl.SELECT_SQL + " and date(trade_time) = date(?) order by trade_time desc",
            BeanPropertyRowMapper.newInstance(TradeDeal.class), date);
    }

}
