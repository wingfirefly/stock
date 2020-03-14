package vip.linhs.stock.api.request;

public abstract class BaseTradeRequest {

    private int userId;

    public BaseTradeRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public abstract String getMethod();

    @Override
    public String toString() {
        return "BaseTradeRequest [userId=" + userId + ", method=" + getMethod() + "]";
    }

    public static enum TradeRequestMethod {
        GetAssertsRequest("get_asserts"), SubmitRequest("submit"), RevokeRequest("revoke"), GetStockList("get_stock_list"),
        GetOrderDataRequest("get_order_data"), GetDealDataRequest("get_deal_data"), AuthenticationRequest("authentication"),
        GetHisDealDataRequest("get_his_deal_data");
        private String value;

        TradeRequestMethod(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

    }

}
