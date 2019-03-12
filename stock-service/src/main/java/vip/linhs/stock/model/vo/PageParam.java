package vip.linhs.stock.model.vo;

import java.util.HashMap;
import java.util.Map;

public class PageParam {

    private int start;
    private int length;
    private Map<String, Object> condition = new HashMap<>();

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void putCondition(Map<String, ?> map) {
        condition.putAll(map);
    }

    public void putCondition(String key, Object value) {
        condition.put(key, value);
    }

}
