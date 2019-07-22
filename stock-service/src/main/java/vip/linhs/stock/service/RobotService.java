package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.Robot;

public interface RobotService {

    Robot getById(int id);

    List<Robot> getListByType(int type);

}
