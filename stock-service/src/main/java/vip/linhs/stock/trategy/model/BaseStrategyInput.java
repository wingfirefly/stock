package vip.linhs.stock.trategy.model;

public class BaseStrategyInput {

    private int userId;

    public BaseStrategyInput(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
