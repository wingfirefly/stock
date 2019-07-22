package vip.linhs.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.RevokeRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitResponse;
import vip.linhs.stock.dao.TradeOrderDao;
import vip.linhs.stock.model.po.Robot;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.service.MessageService;
import vip.linhs.stock.service.RobotService;
import vip.linhs.stock.service.StrategyService;
import vip.linhs.stock.service.TradeApiService;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.StockConsts;

@Service("volumeStrategyServiceImpl")
public class VolumeStrategyServiceImpl implements StrategyService {

    private final Logger logger = LoggerFactory.getLogger(VolumeStrategyServiceImpl.class);

    @Autowired
    private TradeOrderDao tradeOrderDao;

    @Autowired
    private MessageService messageServicve;

    @Autowired
    private RobotService robotService;

    @Autowired
    private TradeApiService tradeApiService;

    @Autowired
    private TradeService tradeService;

    @Override
    public void execute() {
        TradeResultVo<GetDealDataResponse> dealData = tradeApiService.getDealData(new GetDealDataRequest(1));
        if (!dealData.isSuccess()) {
            return;
        }
        List<GetDealDataResponse> dealDataList = dealData.getData();

        List<TradeOrder> tradeOrderList = tradeOrderDao.getAll();

        for (GetDealDataResponse getDealDataResponse : dealDataList) {
            String stockCode = getDealDataResponse.getZqdm();
            int amount = Integer.parseInt(getDealDataResponse.getCjsl());
            double price = Double.parseDouble(getDealDataResponse.getCjjg());
            String cjbh = getDealDataResponse.getCjbh();
            String wtbh = getDealDataResponse.getWtbh();

            double rate = 0.0197;
            TradeRule tradeRule = tradeService.getTradeRuleByStockCode(stockCode);
            if (tradeRule != null) {
                rate = tradeRule.getRate().doubleValue();
            }
            if (SubmitRequest.B.equals(getDealDataResponse.getMmlb())) {
                handleTrade(tradeOrderList, SubmitRequest.S, stockCode, amount, price, 1 + rate, cjbh, wtbh);
            }
            handleTrade(tradeOrderList, SubmitRequest.B, stockCode, amount, price, 1 - rate, cjbh, wtbh);
        }
    }

    private void handleTrade(List<TradeOrder> tradeOrderList, String tradeType, String stockCode, int amount, double price, double rate, String cjbh, String wtbh) {
        TradeOrder needRevokeTradeOrder = getNeedRevokeTradeOrderCode(tradeOrderList, wtbh);
        if (needRevokeTradeOrder != null) {
            String orderCode = needRevokeTradeOrder.getOrderCode();
            String revokes = String.format("%s_%s", DateUtils.formatDate(new Date(), "yyyyMMdd"), orderCode);
            RevokeRequest request = new RevokeRequest(1);
            request.setRevokes(revokes);
            TradeResultVo<RevokeResponse> resultVo = tradeApiService.revoke(request);
            if (!resultVo.isSuccess()) {
                List<Robot> robotList = robotService.getListByType(StockConsts.RobotType.DingDing.value());
                if (!robotList.isEmpty()) {
                    Robot robot = robotList.get(0);
                    String target = robot.getWebhook();
                    messageServicve.sendDingding("revoke: " + resultVo.getMessage(), target);
                }
            }
        }

        if (tradeOrderList.stream().noneMatch(tradeOrder -> cjbh.equals(tradeOrder.getDealCode()) && tradeType.equals(tradeOrder.getTradeType()))) {
            double tradePrice = (int) (price * rate * 100) / 100.0;
            TradeResultVo<SubmitResponse> saleResultVo = trade(stockCode, tradeType, amount, tradePrice);
            if (saleResultVo.isSuccess()) {
                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder.setDealCode(cjbh);
                tradeOrder.setOrderCode(saleResultVo.getData().get(0).getWtbh());
                tradeOrder.setStockCode(stockCode);
                tradeOrder.setTradeType(tradeType);
                tradeOrder.setPrice(new BigDecimal(tradePrice));
                tradeOrder.setVolume(amount);
                tradeOrderDao.save(tradeOrder);
            }
        }
    }

    private TradeOrder getNeedRevokeTradeOrderCode(List<TradeOrder> tradeOrderList, String wtbh) {
        List<TradeOrder> list = tradeOrderList.stream().filter(tradeOrder -> wtbh.equals(tradeOrder.getOrderCode()))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        TradeOrder tradeOrder = list.get(0);
        String dealCode = tradeOrder.getDealCode();
        String tradeType = tradeOrder.getTradeType();
        List<TradeOrder> needRevokeList = list.stream()
                .filter(tradeOrderIn -> dealCode.equals(tradeOrderIn.getDealCode())
                        && tradeType.equals(tradeOrderIn.getTradeType()))
                .collect(Collectors.toList());
        if (needRevokeList.isEmpty()) {
            return null;
        }
        return needRevokeList.get(0);
    }

    private TradeResultVo<SubmitResponse> trade(String code, String tradeType, int amount, double price) {
        SubmitRequest request = new SubmitRequest(1);
        request.setAmount(amount);
        request.setPrice(price);
        request.setTradeType(tradeType);
        request.setStockCode(code);
        TradeResultVo<SubmitResponse> tradeResultVo = tradeApiService.submit(request);
        try {
            List<Robot> robotList = robotService.getListByType(StockConsts.RobotType.DingDing.value());
            if (!robotList.isEmpty()) {
                Robot robot = robotList.get(0);
                String target = robot.getWebhook();
                String body = String.format("%s %s %d %.02f %s", tradeType, code, amount, price, tradeResultVo.getMessage() == null ? "" : tradeResultVo.getMessage());
                messageServicve.sendDingding(body, target);
            }
        } catch (Exception e) {
            logger.error("send dingding error", e);
        }
        return tradeResultVo;
    }

}
