package vip.linhs.stock.parser;

import vip.linhs.stock.model.po.StockInfo;

import java.util.List;

public interface StockInfoParser {

    List<StockInfo> parseStockInfoList(String content);

}
