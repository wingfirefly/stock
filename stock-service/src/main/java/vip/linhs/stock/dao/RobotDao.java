package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.Robot;

public interface RobotDao {

    Robot getById(int id);

    List<Robot> getListByType(int type);

}
