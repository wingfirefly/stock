package vip.linhs.stock.trategy.model;

import vip.linhs.stock.api.request.SubmitRequest;

public class StrategySubmitResult extends SubmitRequest {

    private String relatedDealCode;

    public StrategySubmitResult(int userId) {
        super(userId);
    }

    public String getRelatedDealCode() {
        return relatedDealCode;
    }

    public void setRelatedDealCode(String relatedDealCode) {
        this.relatedDealCode = relatedDealCode;
    }



}
