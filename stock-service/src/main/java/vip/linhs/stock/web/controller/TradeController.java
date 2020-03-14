package vip.linhs.stock.web.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.AuthenticationRequest;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.AuthenticationResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.exception.FieldInputException;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.CommonResponse;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
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
                new FieldInputException();
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
        GetDealDataRequest request = new GetDealDataRequest(1);
        TradeResultVo<GetDealDataResponse> dealData = tradeApiService.getDealData(request);
        if (dealData.isSuccess()) {
            List<DealVo> list = tradeService.getTradeDealList(dealData.getData());
            return new PageVo<>(list, list.size());
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

}
