package vip.linhs.stock.service;

import vip.linhs.stock.model.po.User;
import vip.linhs.stock.model.vo.UserVo;

public interface UserService {

    User login(String username, String password);

    User getByToken(String token);

    UserVo putToSession(User user);

    User getById(int id);

    void update(User user);

}
