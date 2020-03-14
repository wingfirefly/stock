package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.ExecuteInfo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.TaskVo;

public interface TaskService {

    List<ExecuteInfo> getPendingTaskListById(int... id);

    void executeTask(ExecuteInfo executeInfo);

    PageVo<TaskVo> getAllTask(PageParam pageParam);

    void changeTaskState(int state, int id);

}
