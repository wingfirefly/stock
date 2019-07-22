package vip.linhs.stock.api.response;

public class GetDealDataResponse {

    /**
     * 买卖类别-买
     */
    public static final String B = "B";
    /**
     * 买卖类别-卖
     */
    public static final String S = "S";

    /**
     * 委托编号
     */
    private String Wtbh;
    /**
     * 成交编号
     */
    private String Cjbh;
    /**
     * 成交价格
     */
    private String Cjjg;
    /**
     * 成交数量
     */
    private String Cjsl;
    /**
     * 证券代码
     */
    private String Zqdm;
    /**
     * 成交时间 HHmmss
     */
    private String Cjsj;

    /**
     * 买卖类别
     *
     * @see #B
     * @see #S
     */
    private String Mmlb;

    public String getWtbh() {
        return Wtbh;
    }

    public void setWtbh(String wtbh) {
        Wtbh = wtbh;
    }

    public String getCjbh() {
        return Cjbh;
    }

    public void setCjbh(String cjbh) {
        Cjbh = cjbh;
    }

    public String getCjjg() {
        return Cjjg;
    }

    public void setCjjg(String cjjg) {
        Cjjg = cjjg;
    }

    public String getCjsl() {
        return Cjsl;
    }

    public void setCjsl(String cjsl) {
        Cjsl = cjsl;
    }

    public String getCjsj() {
        return Cjsj;
    }

    public void setCjsj(String cjsj) {
        Cjsj = cjsj;
    }

    public String getZqdm() {
        return Zqdm;
    }

    public void setZqdm(String zqdm) {
        Zqdm = zqdm;
    }

    public String getMmlb() {
        return Mmlb;
    }

    public void setMmlb(String mmlb) {
        Mmlb = mmlb;
    }

    @Override
    public String toString() {
        return "GetDealDataResponse [Wtbh=" + Wtbh + ", Cjbh=" + Cjbh + ", Cjjg=" + Cjjg + ", Cjsl="
                + Cjsl + ", Zqdm=" + Zqdm + ", Cjsj=" + Cjsj + ", Mmlb=" + Mmlb + "]";
    }

}
