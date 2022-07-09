package vip.linhs.stock.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.UserDao;
import vip.linhs.stock.model.po.User;
import vip.linhs.stock.service.UserService;
import vip.linhs.stock.util.StockConsts;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
        password = DigestUtils.md5Hex(password);
        return userDao.get(username, password);
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TOKEN, key = "#token", unless="#result.id > 0")
    @Override
    public User getByToken(String token) {
        User user = new User();
        user.setId(-1);
        return user;
    }

    @CachePut(value = StockConsts.CACHE_KEY_TOKEN, key = "#token", unless="#result == null")
    @Override
    public User putToSession(User user, String token) {
        return user;
    }

    @Override
    public User getById(int id) {
        return userDao.get(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

}
