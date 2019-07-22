package vip.linhs.stock.model.po;

import java.math.BigDecimal;

public class TradeRule extends BaseModel {

    private static final long serialVersionUID = 1L;

    private BigDecimal rate;
    private int state;

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

    @Override
    public String toString() {
        return "TradeRule [rate=" + rate + ", state=" + state + ", toString()=" + super.toString() + "]";
    }

}
