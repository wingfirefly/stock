package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeOrderDao;
import vip.linhs.stock.model.po.TradeOrder;

@Repository
public class TradeOrderImpl extends BaseDao implements TradeOrderDao {

    @Override
    public void save(TradeOrder tradeOrder) {
        jdbcTemplate.update(
                "insert into trade_order(deal_code, order_code, stock_code, trade_type, price, volume) values(?, ?, ?, ?, ?, ?)",
                tradeOrder.getDealCode(), tradeOrder.getOrderCode(), tradeOrder.getStockCode(),
                tradeOrder.getTradeType(), tradeOrder.getPrice(), tradeOrder.getVolume());
    }

    @Override
    public List<TradeOrder> getAll() {
        return jdbcTemplate.query(
                "select id, deal_code as dealCode, order_code as orderCode, trade_type as tradeType from trade_order where mark_for_delete = false",
                new Object[] {}, new BeanPropertyRowMapper<>(TradeOrder.class));
    }

}
