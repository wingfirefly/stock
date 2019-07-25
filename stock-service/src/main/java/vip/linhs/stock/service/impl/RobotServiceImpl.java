package vip.linhs.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.RobotDao;
import vip.linhs.stock.model.po.Robot;
import vip.linhs.stock.service.RobotService;
import vip.linhs.stock.util.StockConsts;

@Service
public class RobotServiceImpl implements RobotService {

    @Autowired
    private RobotDao robotDao;

    @Cacheable(value = StockConsts.CACHE_KEY_CONFIG_ROBOT, key = "#id")
    @Override
    public Robot getById(int id) {
        return robotDao.getById(id);
    }

    @Override
    public List<Robot> getListByType(int type) {
        return robotDao.getListByType(type);
    }

}
