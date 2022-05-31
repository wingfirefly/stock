package vip.linhs.stock.api.request;

public class CrGetHisOrdersDataRequest extends GetHisOrdersDataRequest {

    public CrGetHisOrdersDataRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrGetHisOrdersData.value();
    }

}
