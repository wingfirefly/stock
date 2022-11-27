package vip.linhs.stock.trategy.handle;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.CrGetDealDataRequest;
import vip.linhs.stock.api.request.CrGetOrdersDataRequest;
import vip.linhs.stock.api.request.CrRevokeRequest;
import vip.linhs.stock.api.request.CrSubmitRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.CrGetDealDataResponse;
import vip.linhs.stock.api.response.CrGetOrdersDataResponse;
import vip.linhs.stock.api.response.CrRevokeResponse;
import vip.linhs.stock.api.response.CrSubmitResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitResponse;

@Component("crGridStrategyHandler")
public class CrGridStrategyHandler extends GridStrategyHandler {

    @Override
    public TradeResultVo<GetOrdersDataResponse> getOrderData(int userId) {
         TradeResultVo<CrGetOrdersDataResponse> tradeResultVo = tradeApiService.crGetOrdersData(new CrGetOrdersDataRequest(userId));
         return buildResult(tradeResultVo);
    }

    @Override
    public TradeResultVo<GetDealDataResponse> getDealData(int userId) {
        TradeResultVo<CrGetDealDataResponse> tradeResultVo =  tradeApiService.crGetDealData(new CrGetDealDataRequest(userId));
         return buildResult(tradeResultVo);
    }

    @Override
    protected TradeResultVo<SubmitResponse> submit(SubmitRequest request) {
        CrSubmitRequest crRequest = new CrSubmitRequest(request.getUserId());
        BeanUtils.copyProperties(request, crRequest);

        if (SubmitRequest.B.equals(request.getTradeType())) {
            crRequest.setTradeInfo(CrSubmitRequest.xyjylx_rz_b);
        } else {
            crRequest.setTradeInfo(CrSubmitRequest.xyjylx_hk_s);
        }

        TradeResultVo<CrSubmitResponse> tradeResultVo = tradeApiService.crSubmit(crRequest);
        return buildResult(tradeResultVo);
    }

    @Override
    protected TradeResultVo<RevokeResponse> revoke(int userId, String revokes) {
        CrRevokeRequest request = new CrRevokeRequest(userId);
        request.setRevokes(revokes);

        TradeResultVo<CrRevokeResponse> tradeResultVo = tradeApiService.crRevoke(request);
        return buildResult(tradeResultVo);
    }

    @Override
    protected String getFlag() {
        return "cr";
    }

    protected <T> TradeResultVo<T> buildResult(TradeResultVo<? extends T> tradeResultVo) {
        TradeResultVo<T> resultVo =  new TradeResultVo<>();
         resultVo.setStatus(tradeResultVo.getStatus());
         resultVo.setMessage(tradeResultVo.getMessage());
         if (tradeResultVo.success()) {
             resultVo.setData(tradeResultVo.getData().stream().map(v -> v).collect(Collectors.toList()));
         }
         return resultVo;
    }

}
