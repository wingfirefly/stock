package vip.linhs.stock.api.request;

public class CrGetConvertibleBondListV2Request extends GetConvertibleBondListV2Request {

    public CrGetConvertibleBondListV2Request(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrGetConvertibleBondListV2.value();
    }

}
