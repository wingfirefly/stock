package vip.linhs.stock.api.request;

public class CrGetHisDealDataRequest extends GetHisDealDataRequest {

    public CrGetHisDealDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrGetHisDealData.value();
    }

}
