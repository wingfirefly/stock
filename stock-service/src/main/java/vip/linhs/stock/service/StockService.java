package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.model.po.StockLog;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;

public interface StockService {

    List<StockInfo> getAll();

    List<StockInfo> getAllListed();

    void add(List<StockInfo> list);

    void update(List<StockInfo> list);

    void addStockLog(List<StockLog> list);

    void update(List<StockInfo> needAddedList, List<StockInfo> needUpdatedList, List<StockLog> stockLogList);

    void saveDailyIndexToFile(String rootPath);

    void saveDailyIndexFromFile(String rootPath);

    void saveDailyIndex(DailyIndex dailyIndex);

    PageVo<StockInfo> getStockList(PageParam pageParam);

}
