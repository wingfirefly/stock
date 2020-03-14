package vip.linhs.stock.model.po;

public class TradeStockInfoRule extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String stockCode;
    private int ruleId;
    private int state;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
