package vip.linhs.stock.api.request;

public class CrSubmitBatTradeV2Request extends SubmitBatTradeV2Request {

    public CrSubmitBatTradeV2Request(int userId) {
        super(userId);
    }

    public static class CrSubmitData extends SubmitData {
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrSubmitBatTradeV2.value();
    }

}
