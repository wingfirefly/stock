package vip.linhs.stock.service;

import java.util.Date;
import java.util.List;

import vip.linhs.stock.api.response.CrQueryCollateralResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.model.po.StockSelected;
import vip.linhs.stock.model.po.TradeDeal;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeRuleVo;

public interface TradeService {

    TradeMethod getTradeMethodByName(String name);

    List<TradeUser> getTradeUserList();

    TradeUser getTradeUserById(int id);

    void updateTradeUser(TradeUser tradeUser);

    <T extends GetDealDataResponse> List<DealVo> getTradeDealList(List<T> data);

    List<StockVo> getTradeStockList(List<GetStockListResponse> stockList);

    List<StockVo> getCrTradeStockList(List<CrQueryCollateralResponse> stockList);

    List<StockVo> getTradeStockListBySelected(List<StockSelected> selectList);

    <T extends GetOrdersDataResponse> List<OrderVo> getTradeOrderList(List<T> orderList);

    <T extends GetHisDealDataResponse> List<DealVo> getTradeHisDealList(List<T> data);

    PageVo<TradeRuleVo> getTradeRuleList(PageParam pageParam);

    void changeTradeRuleState(int state, int id);

    List<TradeOrder> getLastTradeOrderListByRuleId(int ruleId);

    void saveTradeOrderList(List<TradeOrder> tradeOrderList);

    void resetRule(int id);

    List<TradeDeal> getTradeDealListByDate(Date date);

    void saveTradeDealList(List<TradeDeal> list);

}
