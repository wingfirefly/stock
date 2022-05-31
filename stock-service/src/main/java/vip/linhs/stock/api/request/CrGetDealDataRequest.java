package vip.linhs.stock.api.request;

public class CrGetDealDataRequest extends GetDealDataRequest {

    public CrGetDealDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrGetDealData.value();
    }

}
