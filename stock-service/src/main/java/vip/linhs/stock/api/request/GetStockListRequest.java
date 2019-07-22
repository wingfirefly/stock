package vip.linhs.stock.api.request;

public class GetStockListRequest extends BaseTradeRequest {

    public GetStockListRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetStockList.value();
    }

}
