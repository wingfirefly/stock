package vip.linhs.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.RevokeRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitResponse;
import vip.linhs.stock.exception.ServiceException;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.service.MessageService;
import vip.linhs.stock.service.StockService;
import vip.linhs.stock.service.StrategyService;
import vip.linhs.stock.service.TradeApiService;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.StockUtil;

@Service("volumeStrategyServiceImpl")
public class VolumeStrategyServiceImpl implements StrategyService {

    @Autowired
    private MessageService messageServicve;

    @Autowired
    private TradeApiService tradeApiService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private StockService stockService;

    @Override
    public void execute() {
        TradeResultVo<GetDealDataResponse> dealData = tradeApiService.getDealData(new GetDealDataRequest(1));
        if (!dealData.isSuccess()) {
            throw new ServiceException("execute VolumeStrategyService error: " + dealData.getMessage());
        }
        List<GetDealDataResponse> dealDataList = dealData.getData();

        List<TradeOrder> tradeOrderList = tradeService.getTodayTradeOrderList();

        for (GetDealDataResponse getDealDataResponse : dealDataList) {
            String stockCode = getDealDataResponse.getZqdm();
            TradeRule tradeRule = tradeService.getTradeRuleByStockCode(stockCode);
            if (tradeRule != null) {
                int amount = Integer.parseInt(getDealDataResponse.getCjsl());
                double price = Double.parseDouble(getDealDataResponse.getCjjg());
                String cjbh = getDealDataResponse.getCjbh();
                String wtbh = getDealDataResponse.getWtbh();
                double rate = tradeRule.getRate().doubleValue();
                if (SubmitRequest.B.equals(getDealDataResponse.getMmlb())) {
                    double tradePrice = (int) (price * (1 + rate) * 100) / 100.0;
                    handleTrade(tradeOrderList, SubmitRequest.S, stockCode, amount, tradePrice, cjbh, wtbh);
                }
                double tradePrice = (int) (price * (1 - rate) * 100) / 100.0;
                handleTrade(tradeOrderList, SubmitRequest.B, stockCode, amount, tradePrice, cjbh, wtbh);
            }
        }
    }

    private void handleTrade(List<TradeOrder> tradeOrderList, String tradeType, String stockCode, int amount, double tradePrice, String cjbh, String wtbh) {
        String needRevokeWtbh = getNeedRevokeWtbh(tradeOrderList, wtbh);
        if (!StringUtils.isEmpty(needRevokeWtbh)) {
            String revokes = String.format("%s_%s", DateUtils.formatDate(new Date(), "yyyyMMdd"), needRevokeWtbh);
            RevokeRequest request = new RevokeRequest(1);
            request.setRevokes(revokes);
            TradeResultVo<RevokeResponse> resultVo = tradeApiService.revoke(request);
            if (!resultVo.isSuccess()) {
                messageServicve.sendDingding(String.format("revoke error. request: %s, response: %s", request, resultVo.getMessage()));
            }
        }

        TradeOrder tradeOrderInDb = getByCondition(tradeOrderList, v -> cjbh.equals(v.getTradeCode())
                && tradeType.equals(v.getTradeType()));
        if (tradeOrderInDb == null) {
            TradeResultVo<SubmitResponse> saleResultVo = trade(stockCode, tradeType, amount, tradePrice);
            if (saleResultVo.isSuccess()) {
                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder = new TradeOrder();
                tradeOrder.setTradeCode(cjbh);
                tradeOrder.setStockCode(stockCode);
                tradeOrder.setPrice(new BigDecimal(tradePrice));
                tradeOrder.setVolume(amount);
                tradeOrder.setTradeType(tradeType);
                tradeOrder.setEntrustCode(saleResultVo.getData().get(0).getWtbh());
                tradeService.saveTradeOrder(tradeOrder);
            }
        }
    }

    private String getNeedRevokeWtbh(List<TradeOrder> tradeOrderList, String wtbh) {
        TradeOrder tradeOrder = getByCondition(tradeOrderList, v -> wtbh.equals(v.getEntrustCode()));
        if (tradeOrder == null) {
            return null;
        }

        TradeOrder relatedTradeOrder = getByCondition(tradeOrderList,
                v -> tradeOrder.getTradeCode().equals(v.getTradeCode())
                        && !tradeOrder.getTradeType().equals(v.getTradeType()));
        return relatedTradeOrder != null ? relatedTradeOrder.getEntrustCode() : null;
    }

    private TradeResultVo<SubmitResponse> trade(String code, String tradeType, int amount, double price) {
        SubmitRequest request = new SubmitRequest(1);
        request.setAmount(amount);
        request.setPrice(price);
        request.setTradeType(tradeType);
        request.setStockCode(code);
        TradeResultVo<SubmitResponse> tradeResultVo = tradeApiService.submit(request);
        String name = stockService.getStockByFullCode(StockUtil.getFullCode(code)).getName();
        String body = String.format("%s %s %d %.02f %s", tradeType, name, amount, price, tradeResultVo.getMessage() == null ? "" : tradeResultVo.getMessage());
        messageServicve.sendDingding(body);
        return tradeResultVo;
    }

    private TradeOrder getByCondition(List<TradeOrder> tradeOrderList, Predicate<TradeOrder> predicate) {
        Optional<TradeOrder> optional = tradeOrderList.stream().filter(predicate).findAny();
        return optional.orElse(null);
    }

}
