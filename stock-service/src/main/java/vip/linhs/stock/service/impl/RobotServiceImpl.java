package vip.linhs.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.linhs.stock.dao.RobotDao;
import vip.linhs.stock.model.po.Robot;
import vip.linhs.stock.service.RobotService;

@Service
public class RobotServiceImpl implements RobotService {

    @Autowired
    private RobotDao robotDao;

    @Override
    public Robot getById(int id) {
        return robotDao.getById(id);
    }

    @Override
    public List<Robot> getListByType(int type) {
        return robotDao.getListByType(type);
    }

}
