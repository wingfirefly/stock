package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeRuleDao;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.util.SqlCondition;

@Repository
public class TradeRuleDaoImpl extends BaseDao implements TradeRuleDao {

    private static final String SELECT_SQL = "select id, stock_code as stockCode, strategy_id as strategyId, user_id as userId, type, value, volume, open_price as openPrice, highest_price as highestPrice, lowest_price as lowestPrice, state, description, create_time as createTime, update_time as updateTime from trade_rule where 1 = 1";

    @Override
    public PageVo<TradeRule> get(PageParam pageParam) {
        SqlCondition dataSqlCondition = new SqlCondition(
                TradeRuleDaoImpl.SELECT_SQL,
                pageParam.getCondition());

        int totalRecords = jdbcTemplate.queryForObject(dataSqlCondition.getCountSql(), Integer.class,
                dataSqlCondition.toArgs());

        dataSqlCondition.addSql(" limit ?, ?");
        dataSqlCondition.addPage(pageParam.getStart(), pageParam.getLength());

        List<TradeRule> list = jdbcTemplate.query(dataSqlCondition.toSql(), BeanPropertyRowMapper.newInstance(TradeRule.class),
                dataSqlCondition.toArgs());
        return new PageVo<>(list, totalRecords);
    }

    @Override
    public void updateState(int state, int id) {
        jdbcTemplate.update("update trade_rule set state = ? where id = ?", state, id);
    }

}
