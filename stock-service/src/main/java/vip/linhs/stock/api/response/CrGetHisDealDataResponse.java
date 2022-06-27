package vip.linhs.stock.api.response;

public class CrGetHisDealDataResponse extends GetHisDealDataResponse {

    private String Mmsm;
    private String Xyjylx;

    public String getMmsm() {
        return Mmsm;
    }

    public void setMmsm(String mmsm) {
        Mmsm = mmsm;
    }

    public String getXyjylx() {
        return Xyjylx;
    }

    public void setXyjylx(String xyjylx) {
        Xyjylx = xyjylx;
    }

}
