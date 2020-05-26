package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeConfigVo;

public interface TradeService {

    TradeMethod getTradeMethodByName(String name);

    TradeUser getTradeById(int id);

    void update(TradeUser tradeUser);

    TradeRule getTradeRuleByStockCode(String stockCode);

    PageVo<TradeRule> getRuleList(PageParam pageParam);

    PageVo<TradeConfigVo> getConfigList(PageParam pageParam);

    void changeConfigState(int state, int id);

    List<TradeOrder> getTradeOrderList();

    List<TradeOrder> getTodayTradeOrderList();

    void saveTradeOrder(TradeOrder tradeOrder);

    List<DealVo> getTradeDealList(List<GetDealDataResponse> data);

    void deleteTradeCode(String tradeCode, String tradeType);

    List<StockVo> getTradeStockList(List<GetStockListResponse> stockList);

    List<OrderVo> getTradeOrderList(List<GetOrdersDataResponse> orderList);

    List<DealVo> getTradeHisDealList(List<GetHisDealDataResponse> data);

}
