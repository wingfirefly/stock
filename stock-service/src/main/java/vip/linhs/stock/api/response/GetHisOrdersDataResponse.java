package vip.linhs.stock.api.response;

public class GetHisOrdersDataResponse extends GetOrdersDataResponse {

    /**
     * 委托日期
     */
    private String Wtrq;

    public String getWtrq() {
        return Wtrq;
    }

    public void setWtrq(String wtrq) {
        Wtrq = wtrq;
    }

    @Override
    public String toString() {
        return "GetHisOrdersDataResponse [Wtrq=" + Wtrq + ", super=" + super.toString() + "]";
    }

}
