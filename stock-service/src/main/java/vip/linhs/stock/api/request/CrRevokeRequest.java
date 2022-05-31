package vip.linhs.stock.api.request;

public class CrRevokeRequest extends RevokeRequest {

    public CrRevokeRequest(int userId) {
        super(userId);
    }

    @Override
    public String getMethod() {
        return BaseTradeRequest.TradeRequestMethod.CrRevoke.value();
    }

}
