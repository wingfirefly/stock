package vip.linhs.stock.dao;

import java.util.List;

import vip.linhs.stock.model.po.ExecuteInfo;

public interface ExecuteInfoDao {

    List<ExecuteInfo> getByTaskIdAndState(int[] id, int value);

    void update(ExecuteInfo executeInfo);

}
