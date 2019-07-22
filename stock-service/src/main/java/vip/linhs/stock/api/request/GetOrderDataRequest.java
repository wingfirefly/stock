package vip.linhs.stock.api.request;

public class GetOrderDataRequest extends BaseTradeRequest {

    public GetOrderDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetOrderDataRequest.value();
    }

}
