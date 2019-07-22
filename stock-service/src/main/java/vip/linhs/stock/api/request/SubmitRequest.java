package vip.linhs.stock.api.request;

public class SubmitRequest extends BaseTradeRequest {

    /**
     * 买卖类别-买
     */
    public static final String B = "B";
    /**
     * 买卖类别-卖
     */
    public static final String S = "S";

    private String stockCode;
    private double price;
    private int amount;
    /**
     * 买卖类别
     *
     * @see #B
     * @see #S
     */
    private String tradeType;

    public SubmitRequest(int userId) {
        super(userId);
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.SubmitRequest.value();
    }

}
