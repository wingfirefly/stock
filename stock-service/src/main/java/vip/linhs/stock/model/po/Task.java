package vip.linhs.stock.model.po;

import vip.linhs.stock.exception.ServiceException;

public enum Task {

    BeginOfYear(1, "begin_of_year"), EndOfYear(2, "end_of_year"), BeginOfDay(3, "begin_of_day"),
    EndOfDay(4, "end_of_day"), UpdateOfStock(5, "update_of_stock"),
    UpdateOfStockState(6, "update_of_stock_state"), UpdateOfDailyIndex(7, "update_of_daily_index"),
    Ticker(8, "ticker"), TradeTicker(9, "trade_ticker"), ApplyNewStock(10, "apply_new_stock");

    private int id;
    private String name;

    Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Task valueOf(int id) {
        for(Task task : Task.values()) {
            if (task.id == id) {
                return task;
            }
        }
        throw new ServiceException("no such id of Task");
    }

}
