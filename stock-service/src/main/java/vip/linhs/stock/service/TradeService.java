package vip.linhs.stock.service;

import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.model.po.*;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeRuleVo;

import java.util.Date;
import java.util.List;

public interface TradeService {

    TradeMethod getTradeMethodByName(String name);

    TradeUser getTradeUserById(int id);

    void updateTradeUser(TradeUser tradeUser);

    List<DealVo> getTradeDealList(List<GetDealDataResponse> data);

    List<StockVo> getTradeStockList(List<GetStockListResponse> stockList);

    List<StockVo> getTradeStockListBySelected(List<StockSelected> selectList);

    List<OrderVo> getTradeOrderList(List<GetOrdersDataResponse> orderList);

    List<DealVo> getTradeHisDealList(List<GetHisDealDataResponse> data);

    PageVo<TradeRuleVo> getTradeRuleList(PageParam pageParam);

    void changeTradeRuleState(int state, int id);

    List<TradeOrder> getLastTradeOrderListByRuleId(int ruleId);

    void saveTradeOrderList(List<TradeOrder> tradeOrderList);

    void resetRule(int id);

    List<TradeDeal> getTradeDealListByDate(Date date);

    void saveTradeDealList(List<TradeDeal> list);

}
