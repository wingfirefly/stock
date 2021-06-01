package vip.linhs.stock.api.request;

public class GetConvertibleBondListV2Request extends BaseTradeRequest {

    public GetConvertibleBondListV2Request(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetConvertibleBondListV2.value();
    }

}
