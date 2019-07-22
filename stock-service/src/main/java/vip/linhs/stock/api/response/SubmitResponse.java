package vip.linhs.stock.api.response;

public class SubmitResponse {

    private String Wtbh;

    public String getWtbh() {
        return Wtbh;
    }

    public void setWtbh(String wtbh) {
        Wtbh = wtbh;
    }

    @Override
    public String toString() {
        return "SubmitResponse [Wtbh=" + Wtbh + "]";
    }

}
