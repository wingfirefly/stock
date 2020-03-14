package vip.linhs.stock.service;

import vip.linhs.stock.model.po.Robot;

public interface RobotService {

    Robot getSystem();

    Robot getById(int id);

}
