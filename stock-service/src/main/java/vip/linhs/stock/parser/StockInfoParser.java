package vip.linhs.stock.parser;

import java.util.List;

import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.util.StockConsts.StockState;

public interface StockInfoParser {

    List<StockInfo> parseStockInfoList(String content);

    StockState parseStockState(String content);

}
