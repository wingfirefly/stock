package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeRuleDao;
import vip.linhs.stock.model.po.TradeRule;

@Repository
public class TradeRuleDaoImpl extends BaseDao implements TradeRuleDao {

    @Override
    public TradeRule getTradeRuleByStockCode(String stockCode) {
         List<TradeRule> list = jdbcTemplate.query("select tr.id, tr.rate, tr.state"
                + " from trade_rule tr, trade_stock_info_rule tsr, stock_info si where tr.id = tsr.rule_id and tsr.stock_code = si.code and si.code = ? and tr.state = 1",
                new String[] { stockCode }, BeanPropertyRowMapper.newInstance(TradeRule.class));
         return list.isEmpty() ? null : list.get(0);
    }

}
