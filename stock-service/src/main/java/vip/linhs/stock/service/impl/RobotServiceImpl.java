package vip.linhs.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.RobotDao;
import vip.linhs.stock.model.po.Robot;
import vip.linhs.stock.service.RobotService;
import vip.linhs.stock.util.StockConsts;

@Service
public class RobotServiceImpl implements RobotService {

    private static final String ID_SYSTEM = "1";

    @Autowired
    private RobotDao robotDao;

    @Cacheable(value = StockConsts.CACHE_KEY_CONFIG_ROBOT, key = RobotServiceImpl.ID_SYSTEM)
    @Override
    public Robot getSystem() {
        return getById(Integer.parseInt(RobotServiceImpl.ID_SYSTEM));
    }

    @Cacheable(value = StockConsts.CACHE_KEY_CONFIG_ROBOT, key = "#id")
    @Override
    public Robot getById(int id) {
        return robotDao.getById(id);
    }

}
