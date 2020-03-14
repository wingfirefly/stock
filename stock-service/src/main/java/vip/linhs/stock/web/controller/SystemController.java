package vip.linhs.stock.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.exception.FieldInputException;
import vip.linhs.stock.model.vo.CommonResponse;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.TaskVo;
import vip.linhs.stock.service.TaskService;

@RestController
@RequestMapping("system")
public class SystemController extends BaseController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("taskList")
    public PageVo<TaskVo> getTaskList(PageParam pageParam) {
        return taskService.getAllTask(pageParam);
    }

    @PostMapping("changeTaskState")
    public CommonResponse changeTaskState(int id, int state) {
        FieldInputException e = null;
        if (state != 0 && state != 2) {
            if (e == null) {
                e = new FieldInputException();
            }
            e.addError("state", "state invalid");
        }
        if (id < 0) {
            if (e == null) {
                new FieldInputException();
            }
            e.addError("id", "id invalid");
        }
        if (e != null && e.hasErrors()) {
            throw e;
        }
        taskService.changeTaskState(state, id);
        CommonResponse response = CommonResponse.buildResponse("success");
        return response;
    }

}
