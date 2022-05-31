package vip.linhs.stock.api.request;

public class CrGetCanBuyNewStockListV3Request extends GetCanBuyNewStockListV3Request {

    public CrGetCanBuyNewStockListV3Request(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrGetCanBuyNewStockListV3.value();
    }

}
