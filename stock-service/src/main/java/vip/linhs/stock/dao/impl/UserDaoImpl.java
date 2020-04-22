package vip.linhs.stock.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vip.linhs.stock.dao.BaseDao;
import vip.linhs.stock.dao.UserDao;
import vip.linhs.stock.model.po.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

    private static final String SQL_SELECT = "select id, username, password, name, mobile, email, create_time as createTime, update_time as updateTime from user where 1 = 1";

    @Override
    public User get(String username, String password) {
        List<User> list = jdbcTemplate.query(
                UserDaoImpl.SQL_SELECT + " and username = ? and password = ?",
                new String[] { username, password }, BeanPropertyRowMapper.newInstance(User.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User get(int id) {
        List<User> list = jdbcTemplate.query(
                UserDaoImpl.SQL_SELECT + " and id = ?",
                new Integer[] { id }, BeanPropertyRowMapper.newInstance(User.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("update user set password = ? where id = ?", user.getPassword(), user.getId());
    }

}
