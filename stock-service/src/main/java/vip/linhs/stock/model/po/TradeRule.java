package vip.linhs.stock.model.po;

import java.math.BigDecimal;

public class TradeRule extends BaseModel {

    private static final long serialVersionUID = 1L;

    private BigDecimal rate;
    private int state;
    private String description;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TradeRule [rate=" + rate + ", state=" + state + ", description=" + description + ", BaseModel=" + super.toString() + "]";
    }

}
