package vip.linhs.stock.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class DailyIndex extends BaseModel {

    private static final long serialVersionUID = 1L;

    private int stockInfoId;
    private Date date;
    private BigDecimal preClosingPrice;
    private BigDecimal openingPrice;
    private BigDecimal highestPrice;
    private BigDecimal lowestPrice;
    private BigDecimal closingPrice;
    private long tradingVolume;
    private BigDecimal tradingValue;

    public int getStockInfoId() {
        return stockInfoId;
    }

    public void setStockInfoId(int stockInfoId) {
        this.stockInfoId = stockInfoId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPreClosingPrice() {
        return preClosingPrice;
    }

    public void setPreClosingPrice(BigDecimal preClosingPrice) {
        this.preClosingPrice = preClosingPrice;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public long getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(long tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public BigDecimal getTradingValue() {
        return tradingValue;
    }

    public void setTradingValue(BigDecimal tradingValue) {
        this.tradingValue = tradingValue;
    }

    @Override
    public String toString() {
        return "DailyIndex [stockInfoId=" + stockInfoId + ", date=" + date + ", openingPrice=" + openingPrice
                + ", preClosingPrice=" + preClosingPrice + ", highestPrice=" + highestPrice + ", closingPrice="
                + closingPrice + ", lowestPrice=" + lowestPrice + ", tradingVolume=" + tradingVolume + ", tradingValue="
                + tradingValue + ", BaseModel=" + super.toString() + "]";
    }

}
