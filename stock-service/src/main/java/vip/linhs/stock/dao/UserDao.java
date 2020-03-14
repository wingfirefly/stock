package vip.linhs.stock.dao;

import vip.linhs.stock.model.po.User;

public interface UserDao {

    User get(String username, String password);

    User get(int id);

    void update(User user);

}
