package vip.linhs.stock.web.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.AuthenticationRequest;
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
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.AccountVo;
import vip.linhs.stock.model.vo.CommonResponse;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeConfigVo;
import vip.linhs.stock.service.TradeApiService;
import vip.linhs.stock.service.TradeService;

@RestController
@RequestMapping("trade")
public class TradeController extends BaseController {

    @Autowired
    private TradeApiService tradeApiService;

    @Autowired
    private TradeService tradeService;

    @PostMapping("login")
    public CommonResponse login(int userId, String password, String identifyCode) {
        AuthenticationRequest request = new AuthenticationRequest(userId);
        request.setPassword(password);
        request.setIdentifyCode(identifyCode);

        TradeResultVo<AuthenticationResponse> resultVo = tradeApiService.authentication(request);
        if (resultVo.isSuccess()) {
            AuthenticationResponse response = resultVo.getData().get(0);
            TradeUser tradeUser = new TradeUser();
            tradeUser.setId(request.getUserId());
            tradeUser.setCookie(response.getCookie());
            tradeUser.setValidateKey(response.getValidateKey());
            tradeService.update(tradeUser);
            resultVo.setMessage("success");
        }
        return CommonResponse.buildResponse(resultVo.getMessage());
    }

    @RequestMapping("ruleList")
    public PageVo<TradeRule> getRuleList(PageParam pageParam) {
        return tradeService.getRuleList(pageParam);
    }

    @RequestMapping("configList")
    public PageVo<TradeConfigVo> getConfigList(PageParam pageParam) {
        return tradeService.getConfigList(pageParam);
    }

    @PostMapping("changeConfigState")
    public CommonResponse changeConfigState(int id, int state) {
        FieldInputException e = null;
        if (state != 0 && state != 1) {
            if (e == null) {
                e = new FieldInputException();
            }
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
        tradeService.changeConfigState(state, id);
        CommonResponse response = CommonResponse.buildResponse("success");
        return response;
    }

    @RequestMapping("dealList")
    public PageVo<DealVo> getDealList(PageParam pageParam) {
        GetDealDataRequest request = new GetDealDataRequest(getUserId());
        TradeResultVo<GetDealDataResponse> dealData = tradeApiService.getDealData(request);
        if (dealData.isSuccess()) {
            List<DealVo> list = tradeService.getTradeDealList(dealData.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("hisDealList")
    public PageVo<DealVo> getHisDealList(PageParam pageParam) {
        GetHisDealDataRequest request = new GetHisDealDataRequest(getUserId());
        request.setEt(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateUtils.formatDate(et, "yyyy-MM-dd"));

        TradeResultVo<GetHisDealDataResponse> dealData = tradeApiService.getHisDealData(request);
        if (dealData.isSuccess()) {
            List<DealVo> list = tradeService.getTradeHisDealList(dealData.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("addTradeCode")
    public CommonResponse addTradeCode(String stockCode, String tradeCode, String tradeType) {
        if (!SubmitRequest.S.equals(tradeType) && !SubmitRequest.B.equals(tradeType)) {
            FieldInputException e = new FieldInputException();
            e.addError("tradeType", "tradeType invalid");
            throw e;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setEntrustCode("0000");
        tradeOrder.setPrice(BigDecimal.ZERO);
        tradeOrder.setStockCode(stockCode);
        tradeOrder.setTradeCode(tradeCode);
        tradeOrder.setTradeType(tradeType);
        tradeOrder.setVolume(0);
        tradeService.saveTradeOrder(tradeOrder);
        return CommonResponse.buildResponse("success");
    }

    @RequestMapping("deleteTradeCode")
    public CommonResponse deleteTradeCode(String stockCode, String tradeCode, String tradeType) {
        if (!SubmitRequest.S.equals(tradeType) && !SubmitRequest.B.equals(tradeType)) {
            FieldInputException e = new FieldInputException();
            e.addError("tradeType", "tradeType invalid");
            throw e;
        }
        tradeService.deleteTradeCode(tradeCode, tradeType);
        return CommonResponse.buildResponse("success");
    }

    @RequestMapping("buy")
    public CommonResponse buy(int amount, double price, String stockCode) {
        SubmitRequest request = new SubmitRequest(getUserId());
        request.setAmount(amount);
        request.setPrice(price);
        request.setStockCode(stockCode);
        request.setTradeType(SubmitRequest.B);
        TradeResultVo<SubmitResponse> response = tradeApiService.submit(request);
        return CommonResponse.buildResponse(response.getMessage());
    }

    @RequestMapping("sale")
    public CommonResponse sale(int amount, double price, String stockCode) {
        SubmitRequest request = new SubmitRequest(getUserId());
        request.setAmount(amount);
        request.setPrice(price);
        request.setStockCode(stockCode);
        request.setTradeType(SubmitRequest.S);
        TradeResultVo<SubmitResponse> response = tradeApiService.submit(request);
        return CommonResponse.buildResponse(response.getMessage());
    }

    @RequestMapping("stockList")
    public PageVo<StockVo> getStockList(PageParam pageParam) {
        GetStockListRequest request = new GetStockListRequest(getUserId());
        TradeResultVo<GetStockListResponse> response = tradeApiService.getStockList(request);
        if (response.isSuccess()) {
            List<StockVo> list = tradeService.getTradeStockList(response.getData());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("orderList")
    public PageVo<OrderVo> getOrderList(PageParam pageParam) {
        GetOrdersDataRequest request = new GetOrdersDataRequest(getUserId());
        TradeResultVo<GetOrdersDataResponse> response = tradeApiService.getOrdersData(request);
        if (response.isSuccess()) {
            List<OrderVo> list = tradeService.getTradeOrderList(response.getData());
            list = list.stream().filter(v -> v.getState().equals(GetOrdersDataResponse.YIBAO)).collect(Collectors.toList());
            return new PageVo<>(subList(list, pageParam), list.size());
        }
        return new PageVo<>(Collections.emptyList(), 0);
    }

    @RequestMapping("revoke")
    public CommonResponse revoke(String entrustCode) {
        RevokeRequest request = new RevokeRequest(getUserId());
        String revokes = String.format("%s_%s", DateUtils.formatDate(new Date(), "yyyyMMdd"), entrustCode);
        request.setRevokes(revokes);
        TradeResultVo<RevokeResponse> response = tradeApiService.revoke(request);
        return CommonResponse.buildResponse(response.getMessage());
    }

    @RequestMapping("queryAccount")
    public AccountVo queryAccount() {
        GetAssetsRequest request = new GetAssetsRequest(getUserId());
        TradeResultVo<GetAssetsResponse> tradeResultVo = tradeApiService.getAsserts(request);
        AccountVo accountVo = new AccountVo();
        if (tradeResultVo.isSuccess()) {
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
