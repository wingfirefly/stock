package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeStockInfoRuleDao;
import vip.linhs.stock.model.po.TradeStockInfoRule;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.util.SqlCondition;

@Repository
public class TradeStockInfoRuleDaoImpl extends BaseDao implements TradeStockInfoRuleDao {

    private static final String SELECT_SQL = "select id, stock_code as stockCode, rule_id as ruleId, state, create_time as createTime, update_time as updateTime from trade_stock_info_rule where mark_for_delete = false";

    @Override
    public PageVo<TradeStockInfoRule> get(PageParam pageParam) {
        SqlCondition dataSqlCondition = new SqlCondition(
                TradeStockInfoRuleDaoImpl.SELECT_SQL,
                pageParam.getCondition());

        int totalRecords = jdbcTemplate.queryForObject(dataSqlCondition.getCountSql(),
                dataSqlCondition.toArgs(), Integer.class);

        dataSqlCondition.addSql(" limit ?, ?");
        dataSqlCondition.addPage(pageParam.getStart(), pageParam.getLength());

        List<TradeStockInfoRule> list = jdbcTemplate.query(dataSqlCondition.toSql(),
                dataSqlCondition.toArgs(), BeanPropertyRowMapper.newInstance(TradeStockInfoRule.class));
        return new PageVo<>(list, totalRecords);
    }

    @Override
    public void updateStateById(int state, int id) {
        jdbcTemplate.update("update trade_stock_info_rule set state = ? where id = ?", state, id);
    }

}
