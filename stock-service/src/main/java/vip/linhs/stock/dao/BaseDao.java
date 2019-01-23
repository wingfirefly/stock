package vip.linhs.stock.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
