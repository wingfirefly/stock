package vip.linhs.stock.model.po;

import java.math.BigDecimal;

public class TradeOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String tradeCode;
    private String stockCode;
    private BigDecimal price;
    private int volume;
    private String tradeType;
    private String entrustCode;

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    @Override
    public String toString() {
        return "TradeOrder [tradeCode=" + tradeCode + ", stockCode=" + stockCode + ", price="
                + price + ", volume=" + volume + ", tradeType=" + tradeType + ", entrustCode="
                + entrustCode + ", BaseModel=" + super.toString() + "]";
    }

}
