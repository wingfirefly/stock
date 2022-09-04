package vip.linhs.stock.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.CrGetDealDataRequest;
import vip.linhs.stock.api.request.CrGetHisDealDataRequest;
import vip.linhs.stock.api.request.CrGetOrdersDataRequest;
import vip.linhs.stock.api.request.CrGetRzrqAssertsRequest;
import vip.linhs.stock.api.request.CrQueryCollateralRequest;
import vip.linhs.stock.api.request.CrRevokeRequest;
import vip.linhs.stock.api.request.CrSubmitRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.CrGetDealDataResponse;
import vip.linhs.stock.api.response.CrGetHisDealDataResponse;
import vip.linhs.stock.api.response.CrGetOrdersDataResponse;
import vip.linhs.stock.api.response.CrGetRzrqAssertsResponse;
import vip.linhs.stock.api.response.CrQueryCollateralResponse;
import vip.linhs.stock.api.response.CrRevokeResponse;
import vip.linhs.stock.api.response.CrSubmitResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.model.vo.AccountVo;
import vip.linhs.stock.model.vo.CommonResponse;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.service.TradeApiService;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.StockUtil;

@RestController
@RequestMapping("crTrade")
public class CrTradeController extends BaseController {

    @Autowired
    private TradeApiService tradeApiService;

    @Autowired
    private TradeService tradeService;

    @RequestMapping("dealList")
    public PageVo<DealVo> getDealList(PageParam pageParam) {
        CrGetDealDataRequest request = new CrGetDealDataRequest(getTradeUserId(pageParam.getTradeUserId()));
        TradeResultVo<CrGetDealDataResponse> dealData = tradeApiService.crGetDealData(request);
        if (dealData.success()) {
            List<DealVo> list = tradeService.getTradeDealList(dealData.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("hisDealList")
    public PageVo<DealVo> getHisDealList(PageParam pageParam) {
        CrGetHisDealDataRequest request = new CrGetHisDealDataRequest(getTradeUserId(pageParam.getTradeUserId()));
        request.setEt(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 15 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));

        TradeResultVo<CrGetHisDealDataResponse> dealData = tradeApiService.crGetHisDealData(request);
        if (dealData.success()) {
            List<DealVo> list = tradeService.getTradeDealList(dealData.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("buy")
    public CommonResponse buy(int amount, double price, String stockCode, String stockName, Integer tradeUserId) {
        CrSubmitRequest request = new CrSubmitRequest(getTradeUserId(tradeUserId));
        request.setAmount(amount);
        request.setPrice(price);
        request.setStockCode(stockCode);
        request.setZqmc(stockName);
        request.setTradeType(SubmitRequest.B);
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));
        request.setTradeInfo(CrSubmitRequest.xyjylx_rz_b);
        TradeResultVo<CrSubmitResponse> response = tradeApiService.crSubmit(request);
        String message = response.getMessage();
        if (response.success()) {
            message = response.getData().get(0).getWtbh();
        }

        return CommonResponse.buildResponse(message);
    }

    @RequestMapping("sale")
    public CommonResponse sale(int amount, double price, String stockCode, String stockName, Integer tradeUserId) {
        CrSubmitRequest request = new CrSubmitRequest(getTradeUserId(tradeUserId));
        request.setAmount(amount);
        request.setPrice(price);
        request.setStockCode(stockCode);
        request.setZqmc(stockName);
        request.setTradeType(SubmitRequest.S);
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));
        request.setTradeInfo(CrSubmitRequest.xyjylx_hk_s);
        TradeResultVo<CrSubmitResponse> response = tradeApiService.crSubmit(request);
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));
		String message = response.getMessage();
        if (response.success()) {
            message = response.getData().get(0).getWtbh();
        }

        return CommonResponse.buildResponse(message);
    }

    @RequestMapping("stockList")
    public PageVo<StockVo> getStockList(PageParam pageParam) {
        CrQueryCollateralRequest request = new CrQueryCollateralRequest(getTradeUserId(pageParam.getTradeUserId()));
        TradeResultVo<CrQueryCollateralResponse> response = tradeApiService.crQueryCollateral(request);
        ArrayList<StockVo> list = new ArrayList<>();
        if (response.success()) {
            list.addAll(tradeService.getCrTradeStockList(response.getData()));
        }
        list.sort((a, b) -> Integer.compare(b.getTotalVolume(), a.getTotalVolume()));
        return new PageVo<>(subList(list, pageParam), list.size());
    }

    @RequestMapping("orderList")
    public PageVo<OrderVo> getOrderList(PageParam pageParam) {
        CrGetOrdersDataRequest request = new CrGetOrdersDataRequest(getTradeUserId(pageParam.getTradeUserId()));
        TradeResultVo<CrGetOrdersDataResponse> response = tradeApiService.crGetOrdersData(request);
        if (response.success()) {
            List<OrderVo> list = tradeService.getTradeOrderList(response.getData());
            list = list.stream().filter(v -> v.getState().equals(GetOrdersDataResponse.WEIBAO) || v.getState().equals(GetOrdersDataResponse.YIBAO)).collect(Collectors.toList());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("revoke")
    public CommonResponse revoke(String entrustCode, Integer tradeUserId) {
        CrRevokeRequest request = new CrRevokeRequest(getTradeUserId(tradeUserId));
        String revokes = String.format("%s_%s", DateFormatUtils.format(new Date(), "yyyyMMdd"), entrustCode);
        request.setRevokes(revokes);
        TradeResultVo<CrRevokeResponse> response = tradeApiService.crRevoke(request);
        return CommonResponse.buildResponse(response.getMessage());
    }

    @RequestMapping("queryAccount")
    public AccountVo queryAccount(Integer tradeUserId) {
        CrGetRzrqAssertsRequest request = new CrGetRzrqAssertsRequest(getTradeUserId(tradeUserId));
        TradeResultVo<CrGetRzrqAssertsResponse> tradeResultVo = tradeApiService.crGetRzrqAsserts(request);
        AccountVo accountVo = new AccountVo();
        accountVo.setAvailableAmount(BigDecimal.ZERO);
        accountVo.setFrozenAmount(BigDecimal.ZERO);
        accountVo.setTotalAmount(BigDecimal.ZERO);
        if (tradeResultVo.success()) {
            List<CrGetRzrqAssertsResponse> data = tradeResultVo.getData();
            CrGetRzrqAssertsResponse response = data.get(0);
            if (response.getZjkys() != null) {
                accountVo.setAvailableAmount(new BigDecimal(response.getZjkys()));
            }
            if (response.getBzjkys() != null) {
                accountVo.setFrozenAmount(new BigDecimal(response.getBzjkys()));
            }
            if (response.getBzjkys() != null) {
                accountVo.setFrozenAmount(new BigDecimal(response.getBzjkys()));
            }
            if (response.getZzc() != null) {
                accountVo.setTotalAmount(new BigDecimal(response.getZzc()));
            }
            if (response.getKqzj() != null) {
                accountVo.setWithdrawableAmount(new BigDecimal(response.getKqzj()));
            }
        }
        return accountVo;
    }

}
