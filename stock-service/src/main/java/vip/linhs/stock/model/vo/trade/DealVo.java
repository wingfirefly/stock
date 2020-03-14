package vip.linhs.stock.model.vo.trade;

public class DealVo {

    /**
     * 委托编号
     */
    private String entrustCode;
    /**
     * 成交编号
     */
    private String tradeCode;
    /**
     * 成交价格
     */
    private String price;
    /**
     * 成交数量
     */
    private String volume;
    private String stockCode;
    private String stockName;
    /**
     * 成交时间 HH:mm:ss
     */
    private String tradeTime;
    /**
     * 买卖类别
     *
     * @see #B
     * @see #S
     */
    private String tradeType;

    private String relatedBuyEntrustCode;

    private String relatedSaleEntrustCode;

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getRelatedBuyEntrustCode() {
        return relatedBuyEntrustCode;
    }

    public void setRelatedBuyEntrustCode(String relatedBuyEntrustCode) {
        this.relatedBuyEntrustCode = relatedBuyEntrustCode;
    }

    public String getRelatedSaleEntrustCode() {
        return relatedSaleEntrustCode;
    }

    public void setRelatedSaleEntrustCode(String relatedSaleEntrustCode) {
        this.relatedSaleEntrustCode = relatedSaleEntrustCode;
    }

    @Override
    public String toString() {
        return "DealVo [entrustCode=" + entrustCode + ", tradeCode=" + tradeCode + ", price="
                + price + ", volume=" + volume + ", stockCode=" + stockCode + ", stockName="
                + stockName + ", tradeTime=" + tradeTime + ", tradeType=" + tradeType
                + ", relatedBuyEntrustCode=" + relatedBuyEntrustCode + ", relatedSaleEntrustCode="
                + relatedSaleEntrustCode + "]";
    }

}
