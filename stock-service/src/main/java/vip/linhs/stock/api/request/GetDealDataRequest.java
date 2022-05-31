package vip.linhs.stock.api.request;

public class GetDealDataRequest extends BaseQueryRequest {

    public GetDealDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetDealData.value();
    }

}
