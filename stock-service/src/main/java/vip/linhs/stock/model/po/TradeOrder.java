package vip.linhs.stock.model.po;

import java.math.BigDecimal;

public class TradeOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String orderCode;
    private String dealCode;
    private String stockCode;
    private String tradeType;
    private BigDecimal price;
    private int volume;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDealCode() {
        return dealCode;
    }

    public void setDealCode(String dealCode) {
        this.dealCode = dealCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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

    @Override
    public String toString() {
        return "TradeOrder [orderCode=" + orderCode + ", stockCode=" + stockCode + ", dealCode=" + dealCode
                + ", tradeType=" + tradeType + ", price=" + price + ", volume=" + volume + "]";
    }

}
