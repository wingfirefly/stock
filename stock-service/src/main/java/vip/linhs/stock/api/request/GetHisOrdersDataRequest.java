package vip.linhs.stock.api.request;

public class GetHisOrdersDataRequest extends BaseTradeRequest {

    private String st;
    private String et;

    public GetHisOrdersDataRequest(int userId) {
        super(userId);
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.GetHisOrdersDataRequest.value();
    }

    @Override
    public String toString() {
        return "GetHisOrdersDataRequest [st=" + st + ", et=" + et + "]";
    }

}
