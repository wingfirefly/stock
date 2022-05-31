package vip.linhs.stock.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.AuthenticationRequest;
import vip.linhs.stock.api.request.BaseTradeRequest;
import vip.linhs.stock.api.request.GetAssetsRequest;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.GetHisDealDataRequest;
import vip.linhs.stock.api.request.GetOrdersDataRequest;
import vip.linhs.stock.api.request.GetStockListRequest;
import vip.linhs.stock.api.request.RevokeRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.AuthenticationResponse;
import vip.linhs.stock.api.response.GetAssetsResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitResponse;
import vip.linhs.stock.exception.FieldInputException;
import vip.linhs.stock.model.po.StockSelected;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.AccountVo;
import vip.linhs.stock.model.vo.CommonResponse;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeRuleVo;
import vip.linhs.stock.service.StockSelectedService;
import vip.linhs.stock.service.TradeApiService;
import vip.linhs.stock.service.TradeService;

@RestController
@RequestMapping("trade")
public class TradeController extends BaseController {

    @Autowired
    private TradeApiService tradeApiService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private StockSelectedService stockSelectedService;

    @RequestMapping("queryVerifyCodeUrl")
    public CommonResponse queryVerifyCodeUrl() {
        TradeMethod tradeMethod = tradeService.getTradeMethodByName(BaseTradeRequest.TradeRequestMethod.YZM.value());
        String url = tradeMethod.getUrl();
        return CommonResponse.buildResponse(url);
    }

    @PostMapping("login")
    public CommonResponse login(int userId, String password, String identifyCode, String randNum) {
        AuthenticationRequest request = new AuthenticationRequest(userId);
        // password = "UxvVPgyg3CPqWfeDWKkPUEcuWVcNZYXCGXsKcMdOxccJ294Mr0ATkxAnnCONS24+4l2+dclRcSzR3dLXz2H/oSzXQ0PG6Sxaa0zAw1jNR2oiSH+ORbQhK76LStdeYdxwV5gsZd0KuwC+ugqXmEM9lN7ODw7kW3Bjc8ybRoV5hQI=";
        request.setPassword(password);
        request.setIdentifyCode(identifyCode);
        request.setRandNumber(randNum);

        TradeResultVo<AuthenticationResponse> resultVo = tradeApiService.authentication(request);
        if (resultVo.success()) {
            AuthenticationResponse response = resultVo.getData().get(0);
            TradeUser tradeUser = new TradeUser();
            tradeUser.setId(request.getUserId());
            tradeUser.setCookie(response.getCookie());
            tradeUser.setValidateKey(response.getValidateKey());
            tradeService.updateTradeUser(tradeUser);
            resultVo.setMessage(CommonResponse.DEFAULT_MESSAGE_SUCCESS);
        }
        return CommonResponse.buildResponse(resultVo.getMessage());
    }

    @RequestMapping("ruleList")
    public PageVo<TradeRuleVo> getRuleList(PageParam pageParam) {
        return tradeService.getTradeRuleList(pageParam);
    }

    @PostMapping("changeRuleState")
    public CommonResponse changeRuleState(int id, int state) {
        FieldInputException e = null;
        if (state != 0 && state != 1) {
            e = new FieldInputException();
            e.addError("state", "state invalid");
        }
        if (id < 0) {
            if (e == null) {
                e = new FieldInputException();
            }
            e.addError("id", "id invalid");
        }
        if (e != null && e.hasErrors()) {
            throw e;
        }
        tradeService.changeTradeRuleState(state, id);
        CommonResponse response = CommonResponse.buildResponse(CommonResponse.DEFAULT_MESSAGE_SUCCESS);
        return response;
    }

    @RequestMapping("resetRule")
    public CommonResponse resetRule(int id) {
        FieldInputException e = null;
        if (id < 0) {
            e = new FieldInputException();
            e.addError("id", "id invalid");
        }
        if (e != null && e.hasErrors()) {
            throw e;
        }
        tradeService.resetRule(id);
        return CommonResponse.buildResponse(CommonResponse.DEFAULT_MESSAGE_SUCCESS);
    }

    @RequestMapping("dealList")
    public PageVo<DealVo> getDealList(PageParam pageParam) {
        GetDealDataRequest request = new GetDealDataRequest(getUserId());
        TradeResultVo<GetDealDataResponse> dealData = tradeApiService.getDealData(request);
        if (dealData.success()) {
            List<DealVo> list = tradeService.getTradeDealList(dealData.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("hisDealList")
    public PageVo<DealVo> getHisDealList(PageParam pageParam) {
        GetHisDealDataRequest request = new GetHisDealDataRequest(getUserId());
        request.setEt(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 15 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));

        TradeResultVo<GetHisDealDataResponse> dealData = tradeApiService.getHisDealData(request);
        if (dealData.success()) {
            List<DealVo> list = tradeService.getTradeHisDealList(dealData.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("buy")
    public CommonResponse buy(int amount, double price, String stockCode, String stockName) {
        SubmitRequest request = new SubmitRequest(getUserId());
        request.setAmount(amount);
        request.setPrice(price);
        request.setStockCode(stockCode);
        request.setZqmc(stockName);
        request.setTradeType(SubmitRequest.B);
        TradeResultVo<SubmitResponse> response = tradeApiService.submit(request);
        String message = response.getMessage();
        if (response.success()) {
            message = response.getData().get(0).getWtbh();
        }

        return CommonResponse.buildResponse(message);
    }

    @RequestMapping("sale")
    public CommonResponse sale(int amount, double price, String stockCode, String stockName) {
        SubmitRequest request = new SubmitRequest(getUserId());
        request.setAmount(amount);
        request.setPrice(price);
        request.setStockCode(stockCode);
        request.setZqmc(stockName);
        request.setTradeType(SubmitRequest.S);
        TradeResultVo<SubmitResponse> response = tradeApiService.submit(request);
        String message = response.getMessage();
        if (response.success()) {
            message = response.getData().get(0).getWtbh();
        }

        return CommonResponse.buildResponse(message);
    }

    @RequestMapping("stockList")
    public PageVo<StockVo> getStockList(PageParam pageParam) {
        GetStockListRequest request = new GetStockListRequest(getUserId());
        TradeResultVo<GetStockListResponse> response = tradeApiService.getStockList(request);
        ArrayList<StockVo> list = new ArrayList<>();
        if (response.success()) {
            list.addAll(tradeService.getTradeStockList(response.getData()));
        }
        List<StockSelected> selectList = stockSelectedService.getList();
        selectList = selectList.stream().filter(v -> {
            return list.stream().noneMatch(vo -> vo.getStockCode().equals(v.getCode()));
        }).collect(Collectors.toList());
        list.addAll(tradeService.getTradeStockListBySelected(selectList));
        list.sort((a, b) -> Integer.compare(b.getTotalVolume(), a.getTotalVolume()));
        return new PageVo<>(subList(list, pageParam), list.size());
    }

    @RequestMapping("orderList")
    public PageVo<OrderVo> getOrderList(PageParam pageParam) {
        GetOrdersDataRequest request = new GetOrdersDataRequest(getUserId());
        TradeResultVo<GetOrdersDataResponse> response = tradeApiService.getOrdersData(request);
        if (response.success()) {
            List<OrderVo> list = tradeService.getTradeOrderList(response.getData());
            list = list.stream().filter(v -> v.getState().equals(GetOrdersDataResponse.WEIBAO) || v.getState().equals(GetOrdersDataResponse.YIBAO)).collect(Collectors.toList());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("revoke")
    public CommonResponse revoke(String entrustCode) {
        RevokeRequest request = new RevokeRequest(getUserId());
        String revokes = String.format("%s_%s", DateFormatUtils.format(new Date(), "yyyyMMdd"), entrustCode);
        request.setRevokes(revokes);
        TradeResultVo<RevokeResponse> response = tradeApiService.revoke(request);
        return CommonResponse.buildResponse(response.getMessage());
    }

    @RequestMapping("queryAccount")
    public AccountVo queryAccount() {
        GetAssetsRequest request = new GetAssetsRequest(getUserId());
        TradeResultVo<GetAssetsResponse> tradeResultVo = tradeApiService.getAsserts(request);
        AccountVo accountVo = new AccountVo();
        if (tradeResultVo.success()) {
            List<GetAssetsResponse> data = tradeResultVo.getData();
            GetAssetsResponse response = data.get(0);
            accountVo.setAvailableAmount(new BigDecimal(response.getKyzj()));
            accountVo.setFrozenAmount(new BigDecimal(response.getDjzj()));
            accountVo.setTotalAmount(new BigDecimal(response.getZzc()));
            accountVo.setWithdrawableAmount(new BigDecimal(response.getKqzj()));
        } else {
            accountVo.setAvailableAmount(BigDecimal.ZERO);
            accountVo.setFrozenAmount(BigDecimal.ZERO);
            accountVo.setTotalAmount(BigDecimal.ZERO);
            accountVo.setWithdrawableAmount(BigDecimal.ZERO);
        }
        return accountVo;
    }

}
