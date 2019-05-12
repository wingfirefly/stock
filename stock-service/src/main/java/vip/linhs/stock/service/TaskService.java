package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.ExecuteInfo;

public interface TaskService {

    List<ExecuteInfo> getPendingTaskListById(int... id);

    void executeTask(ExecuteInfo executeInfo);

}
