package vip.linhs.stock.api;

import java.util.List;

public class TradeResultVo<T> {

    public static final int STATUS_SUCCESS = 0;

    private String Message;
    private int Status;
    private List<T> Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "TradeResultVo [Message=" + Message + ", Status=" + Status + ", Data=" + Data + "]";
    }

    public boolean isSuccess() {
        return TradeResultVo.isSuccess(Status);
    }

    public static boolean isSuccess(int status) {
        return status == TradeResultVo.STATUS_SUCCESS;
    }

}
