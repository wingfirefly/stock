package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.ExecuteInfo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.TaskVo;

public interface ExecuteInfoDao {

    List<ExecuteInfo> getByTaskIdAndState(int[] id, Integer value);

    void update(ExecuteInfo executeInfo);

    PageVo<TaskVo> get(PageParam pageParam);

    void updateState(int state, int id);

}
