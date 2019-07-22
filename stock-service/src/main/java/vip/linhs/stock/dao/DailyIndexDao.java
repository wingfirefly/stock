package vip.linhs.stock.dao;

import java.util.Date;
import java.util.List;

import vip.linhs.stock.model.po.DailyIndex;

public interface DailyIndexDao {

    int save(DailyIndex dailyIndex);

    void save(List<DailyIndex> list);

    DailyIndex getDailyIndexByFullCodeAndDate(String fullCode, Date date);

}
