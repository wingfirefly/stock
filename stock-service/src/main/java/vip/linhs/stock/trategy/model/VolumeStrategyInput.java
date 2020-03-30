package vip.linhs.stock.trategy.model;

import java.util.List;

import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.model.po.TradeOrder;

public class VolumeStrategyInput extends BaseStrategyInput {

    public VolumeStrategyInput(int userId) {
        super(userId);
    }

    private List<GetDealDataResponse> dealDataList;

    private List<TradeOrder> tradeOrderList;

    public List<GetDealDataResponse> getDealDataList() {
        return dealDataList;
    }

    public void setDealDataList(List<GetDealDataResponse> dealDataList) {
        this.dealDataList = dealDataList;
    }

    public List<TradeOrder> getTradeOrderList() {
        return tradeOrderList;
    }

    public void setTradeOrderList(List<TradeOrder> tradeOrderList) {
        this.tradeOrderList = tradeOrderList;
    }



}
