package vip.linhs.stock.service;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.po.StockInfo;

import java.util.List;

public interface StockCrawlerService {

    List<StockInfo> getStockList();

    DailyIndex getDailyIndex(String code);

    List<DailyIndex> getDailyIndex(List<String> codeList);

    List<DailyIndex> getHistoryDailyIndexs(String code);

    String getHistoryDailyIndexsString(String code);

}
