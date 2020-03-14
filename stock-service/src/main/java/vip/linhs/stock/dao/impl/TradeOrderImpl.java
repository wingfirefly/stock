package vip.linhs.stock.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeOrderDao;
import vip.linhs.stock.model.po.TradeOrder;

@Repository
public class TradeOrderImpl extends BaseDao implements TradeOrderDao {

    private static final String SELECT_SQL = "select id, trade_code as tradeCode, stock_code as stockCode, price, volume, trade_type as tradeType, entrust_code as entrustCode, create_time as createTime, update_time as updateTime from trade_order where mark_for_delete = false";

    @Override
    public void save(TradeOrder tradeOrder) {
        jdbcTemplate.update(
                "insert into trade_order(trade_code, stock_code, price, volume, trade_type, entrust_code) values(?, ?, ?, ?, ?, ?)",
                tradeOrder.getTradeCode(), tradeOrder.getStockCode(), tradeOrder.getPrice(),
                tradeOrder.getVolume(), tradeOrder.getTradeType(), tradeOrder.getEntrustCode());
    }

    @Override
    public List<TradeOrder> getAll() {
        return jdbcTemplate.query(TradeOrderImpl.SELECT_SQL, new Object[] {},
                new BeanPropertyRowMapper<>(TradeOrder.class));
    }

    @Override
    public List<TradeOrder> getListByDate(Date date) {
        return jdbcTemplate.query(TradeOrderImpl.SELECT_SQL + " and create_time >= ?",
                new Object[] { new java.sql.Date(date.getTime()) },
                new BeanPropertyRowMapper<>(TradeOrder.class));
    }

    @Override
    public void delete(String tradeCode, String tradeType) {
        jdbcTemplate.update("delete from trade_order where trade_code = ? and trade_type = ?", tradeCode, tradeType);
    }

}
