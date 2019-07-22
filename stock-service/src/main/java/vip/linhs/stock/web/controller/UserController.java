package vip.linhs.stock.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.AuthenticationRequest;
import vip.linhs.stock.api.response.AuthenticationResponse;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.CommonResponse;
import vip.linhs.stock.service.TradeApiService;
import vip.linhs.stock.service.TradeService;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private TradeApiService tradeApiService;

    @Autowired
    private TradeService tradeService;

    @PostMapping("trade/login")
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
        CommonResponse response = CommonResponse.buildResponse(resultVo.getMessage());
        return response;
    }
}
