package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.util.StockConsts.StockState;

public interface StockCrawlerService {

    List<StockInfo> getStockList();

    StockState getStockState(String code);

    DailyIndex getDailyIndex(String code);

    List<DailyIndex> getHistoryDailyIndexs(String code);

    String getHistoryDailyIndexsString(String code);

}
