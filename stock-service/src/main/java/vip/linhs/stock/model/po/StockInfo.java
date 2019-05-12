package vip.linhs.stock.model.po;

public class StockInfo extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private String exchange;
    private String abbreviation;
    private int state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StockInfo [code=" + code + ", name=" + name + ", exchange=" + exchange + ", abbreviation="
                + abbreviation + ", state=" + state + ", BaseModel=" + super.toString() + "]";
    }

}
