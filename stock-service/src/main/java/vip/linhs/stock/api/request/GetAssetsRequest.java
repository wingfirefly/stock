package vip.linhs.stock.api.request;

public class GetAssetsRequest extends BaseTradeRequest {

    public GetAssetsRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetAssertsRequest.value();
    }

}
