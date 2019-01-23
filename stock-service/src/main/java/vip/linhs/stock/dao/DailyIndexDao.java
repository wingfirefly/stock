package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.DailyIndex;

public interface DailyIndexDao {

    int save(DailyIndex dailyIndex);

    void save(List<DailyIndex> list);

}
