package vip.linhs.stock.api.response;

public class GetStockListResponse {

    private String Zqmc;
    private String Zqdm;
    private String Zqsl;
    private String Zxjg;
    private String Kysl;
    private String Cbjg;
    private String Ljyk;

    public String getZqmc() {
        return Zqmc;
    }

    public void setZqmc(String zqmc) {
        Zqmc = zqmc;
    }

    public String getZqdm() {
        return Zqdm;
    }

    public void setZqdm(String zqdm) {
        Zqdm = zqdm;
    }

    public String getZqsl() {
        return Zqsl;
    }

    public void setZqsl(String zqsl) {
        Zqsl = zqsl;
    }

    public String getZxjg() {
        return Zxjg;
    }

    public void setZxjg(String zxjg) {
        Zxjg = zxjg;
    }

    public String getKysl() {
        return Kysl;
    }

    public void setKysl(String kysl) {
        Kysl = kysl;
    }

    public String getCbjg() {
        return Cbjg;
    }

    public void setCbjg(String cbjg) {
        Cbjg = cbjg;
    }

    public String getLjyk() {
        return Ljyk;
    }

    public void setLjyk(String ljyk) {
        Ljyk = ljyk;
    }

    @Override
    public String toString() {
        return "GetStockListResponse [Zqmc=" + Zqmc + ", Zqdm=" + Zqdm + ", Zqsl=" + Zqsl + ", Zxjg=" + Zxjg + ", Kysl="
                + Kysl + ", Cbjg=" + Cbjg + ", Ljyk=" + Ljyk + "]";
    }

}
