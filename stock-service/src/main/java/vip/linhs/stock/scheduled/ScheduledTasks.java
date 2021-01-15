package vip.linhs.stock.scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.GetAssetsRequest;
import vip.linhs.stock.api.response.GetAssetsResponse;
import vip.linhs.stock.model.po.ExecuteInfo;
import vip.linhs.stock.model.po.Task;
import vip.linhs.stock.service.HolidayCalendarService;
import vip.linhs.stock.service.TaskService;
import vip.linhs.stock.service.TradeApiService;

@Component
public class ScheduledTasks {

    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private HolidayCalendarService holidayCalendarService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TradeApiService tradeApiService;

    /**
     * begin of year
     */
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void runBeginOfYear() {
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.BeginOfYear.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runBeginOfYear error", e);
        }
    }

    /**
     * end of year
     */
    @Scheduled(cron = "0 0 23 31 12 ?")
    public void runEndOfYear() {
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.EndOfYear.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runEndOfYear error", e);
        }
    }

    /**
     * begin of day
     */
    @Scheduled(cron = "0 0 6 ? * MON-FRI")
    public void runBeginOfDay() {
        boolean isHoliday = holidayCalendarService.isHoliday(new Date());
        if (isHoliday) {
            return;
        }
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.BeginOfDay.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runBeginOfDay error", e);
        }
    }

    /**
     * end of day
     */
    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    public void runEndOfDay() {
        boolean isHoliday = holidayCalendarService.isHoliday(new Date());
        if (isHoliday) {
            return;
        }
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.EndOfDay.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runEndOfDay error", e);
        }
    }

    /**
     * update of stock
     */
    @Scheduled(cron = "0 0 9 ? * MON-FRI")
    public void runUpdateOfStock() {
        boolean isHoliday = holidayCalendarService.isHoliday(new Date());
        if (isHoliday) {
            return;
        }
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.UpdateOfStock.getId(),
                    Task.UpdateOfStockState.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runUpdateOfStock error", e);
        }
    }

    /**
     * update of daily index
     */
    @Scheduled(cron = "0 0 17,18,19 ? * MON-FRI")
    public void runUpdateOfDailyIndex() {
        boolean isHoliday = holidayCalendarService.isHoliday(new Date());
        if (isHoliday) {
            return;
        }
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.UpdateOfDailyIndex.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runUpdateOfDailyIndex error", e);
        }
    }

    /**
     * ticker
     */
    @Scheduled(cron = "0,15,30,45 * 9,10,11,13,14 ? * MON-FRI")
    public void runTicker() {
        boolean isHoliday = holidayCalendarService.isHoliday(new Date());
        if (isHoliday) {
            return;
        }
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if (hour == 9 && minute < 30 || hour == 11 && minute > 30) {
             return;
        }
        try {
            List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.Ticker.getId(), Task.TradeTicker.getId());
            executeTask(list);
        } catch (Exception e) {
            logger.error("task runTicker error", e);
        }
    }

    private void executeTask(List<ExecuteInfo> list) {
        for (ExecuteInfo executeInfo : list) {
            taskService.executeTask(executeInfo);
        }
    }

    @Scheduled(cron = "0 0,20,40 * ? * MON-FRI")
    public void heartbeat() {
        boolean isHoliday = holidayCalendarService.isHoliday(new Date());
        if (isHoliday) {
            return;
        }
        List<ExecuteInfo> list = taskService.getPendingTaskListById(Task.TradeTicker.getId());
        if (!list.isEmpty()) {
            TradeResultVo<GetAssetsResponse> tradeResultVo = tradeApiService.getAsserts(new GetAssetsRequest(1));
            if (!tradeResultVo.isSuccess()) {
                logger.error("heartbeat: " + tradeResultVo.getMessage());
            }
        }
    }

}
