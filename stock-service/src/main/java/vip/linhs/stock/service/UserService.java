package vip.linhs.stock.service;

import vip.linhs.stock.model.po.User;

public interface UserService {

    User login(String username, String password);

    User getByToken(String token);

    User putToSession(User user, String token);

    User getById(int id);

    void update(User user);

}
