package vip.linhs.stock.api.request;

public class GetDealDataRequest extends BaseTradeRequest {

    public GetDealDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetDealDataRequest.value();
    }

    @Override
    public String toString() {
        return "GetDealDataRequest [" + super.toString() + "]";
    }

}
