package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.SystemConfig;

public interface SystemConfigDao {

    List<SystemConfig> getByName(String name);

    List<SystemConfig> getAll();

}
