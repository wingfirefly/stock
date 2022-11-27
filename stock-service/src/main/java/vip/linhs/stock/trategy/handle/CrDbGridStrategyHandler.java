package vip.linhs.stock.trategy.handle;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.CrSubmitRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.CrSubmitResponse;
import vip.linhs.stock.api.response.SubmitResponse;

@Component("crDbGridStrategyHandler")
public class CrDbGridStrategyHandler extends CrGridStrategyHandler {
    
    @Override
    protected TradeResultVo<SubmitResponse> submit(SubmitRequest request) {
        CrSubmitRequest crRequest = new CrSubmitRequest(request.getUserId());
        BeanUtils.copyProperties(request, crRequest);

        if (SubmitRequest.B.equals(request.getTradeType())) {
            crRequest.setTradeInfo(CrSubmitRequest.xyjylx_db_b);
        } else {
            crRequest.setTradeInfo(CrSubmitRequest.xyjylx_db_s);
        }

        TradeResultVo<CrSubmitResponse> tradeResultVo = tradeApiService.crSubmit(crRequest);
        return buildResult(tradeResultVo);
    }
}
