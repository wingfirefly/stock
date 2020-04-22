package vip.linhs.stock.dao;

import java.util.Date;
import java.util.List;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.vo.DailyIndexVo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;

public interface DailyIndexDao {

    int save(DailyIndex dailyIndex);

    void save(List<DailyIndex> list);

    PageVo<DailyIndexVo> getDailyIndexList(PageParam pageParam);

    List<DailyIndex> getDailyIndexListByDate(Date date);

}
