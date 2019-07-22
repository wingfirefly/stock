package vip.linhs.stock.model.po;

public class TickerConfig extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String key;
    private String value;
    private int robotId;
    private int state;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRobotId() {
        return robotId;
    }

    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
