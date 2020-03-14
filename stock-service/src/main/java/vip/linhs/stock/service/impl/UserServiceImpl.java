package vip.linhs.stock.service.impl;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.dao.UserDao;
import vip.linhs.stock.model.po.User;
import vip.linhs.stock.model.vo.UserVo;
import vip.linhs.stock.service.RedisClient;
import vip.linhs.stock.service.UserService;
import vip.linhs.stock.util.StockConsts;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisClient redisClient;

    @Override
    public User login(String username, String password) {
        password = DigestUtils.md5Hex(password);
        return userDao.get(username, password);
    }

    @Override
    public User getByToken(String token) {
        String value = redisClient.get(StockConsts.CACHE_KEY_TOKEN + ":" + token);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSON.parseObject(value, User.class);
    }

    @Override
    public UserVo putToSession(User user) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        UserVo userVo = new UserVo();
        userVo.setToken(token);
        userVo.setEmail(user.getEmail());
        userVo.setMobile(user.getMobile());
        userVo.setName(user.getName());
        userVo.setUsername(user.getUsername());
        userVo.setToken(token);

        redisClient.put(StockConsts.CACHE_KEY_TOKEN + ":" + token, JSON.toJSONString(user));
        return userVo;
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
