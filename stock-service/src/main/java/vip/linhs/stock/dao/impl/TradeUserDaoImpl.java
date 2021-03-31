package vip.linhs.stock.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.TradeUserDao;
import vip.linhs.stock.model.po.TradeUser;

@Repository
public class TradeUserDaoImpl extends BaseDao implements TradeUserDao {

    @Override
    public TradeUser getById(int id) {
        return jdbcTemplate.queryForObject(
                "select id, name, account_id as accountId, cookie, validate_key as validateKey from trade_user where id = ?",
                BeanPropertyRowMapper.newInstance(TradeUser.class), id);
    }

    @Override
    public void update(TradeUser tradeUser) {
        jdbcTemplate.update("update trade_user set cookie = ?, validate_key = ? where id = ?",
                tradeUser.getCookie(), tradeUser.getValidateKey(), tradeUser.getId());
    }

}
