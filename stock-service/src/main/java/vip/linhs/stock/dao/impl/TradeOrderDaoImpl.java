package vip.linhs.stock.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeOrderDao;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.util.StockConsts;

@Repository
public class TradeOrderDaoImpl extends BaseDao implements TradeOrderDao {

    private static final String SELECT_SQL = "select id, rule_id as ruleId, stock_code as stockCode, entrust_code as entrustCode, deal_code as dealCode, related_deal_code as relatedDealCode, price, volume, trade_type as tradeType, trade_state as tradeState, trade_time as tradeTime, state, create_time as createTime, update_time as updateTime from trade_order where 1 = 1";

    @Override
    public void add(TradeOrder tradeOrder) {
        jdbcTemplate.update(
                "insert into trade_order(rule_id, stock_code, entrust_code, deal_code, related_deal_code, price, volume, trade_type, trade_state, trade_time, state, user_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                tradeOrder.getRuleId(), tradeOrder.getStockCode(), tradeOrder.getEntrustCode(),
                tradeOrder.getDealCode(), tradeOrder.getRelatedDealCode(), tradeOrder.getPrice(),
                tradeOrder.getVolume(), tradeOrder.getTradeType(), tradeOrder.getTradeState(),
                tradeOrder.getTradeTime(), tradeOrder.getState(), 1);
    }

    @Override
    public void update(TradeOrder tradeOrder) {
        jdbcTemplate.update(
                "UPDATE trade_order SET deal_code = ?, price = ?, volume = ?, trade_type = ?, trade_state = ?, trade_time = ? where id  = ?",
                tradeOrder.getDealCode(), tradeOrder.getPrice(), tradeOrder.getVolume(),
                tradeOrder.getTradeType(), tradeOrder.getTradeState(), tradeOrder.getTradeTime(), tradeOrder.getId());
    }

    @Override
    public List<TradeOrder> getLastListByRuleId(int ruleId, int userId) {
        return jdbcTemplate.query(TradeOrderDaoImpl.SELECT_SQL + " and rule_id = ? and state = ? and user_id = ? and trade_state in (?, ?) and trade_time >= DATE_SUB(date(?), INTERVAL 60 day) order by trade_time desc limit 0, 60",
                BeanPropertyRowMapper.newInstance(TradeOrder.class),
                ruleId, userId, StockConsts.TradeState.Valid.value(),
                GetOrdersDataResponse.YIBAO, GetOrdersDataResponse.YICHENG, new Date()
            );
    }

    @Override
    public void setInvalidByRuleId(int ruleId) {
        jdbcTemplate.update("update trade_order set state = ? where rule_id = ? and state <> ?", StockConsts.TradeState.Invalid.value(), ruleId, StockConsts.TradeState.Invalid.value());
    }

}
