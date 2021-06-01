package vip.linhs.stock.api.request;

import java.util.List;

public abstract class BaseTradeListRequest extends BaseTradeRequest {

    public BaseTradeListRequest(int userId) {
        super(userId);
    }

    public abstract List<?> getList();

}
