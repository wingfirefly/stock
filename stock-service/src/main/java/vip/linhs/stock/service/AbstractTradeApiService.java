package vip.linhs.stock.service;

import com.alibaba.fastjson.TypeReference;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.BaseTradeRequest;
import vip.linhs.stock.api.request.GetAssetsRequest;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.GetOrderDataRequest;
import vip.linhs.stock.api.request.GetStockListRequest;
import vip.linhs.stock.api.request.RevokeRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.GetAssetsResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetOrderDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitResponse;

public abstract class AbstractTradeApiService implements TradeApiService {

    @Override
    public TradeResultVo<GetAssetsResponse> getAsserts(GetAssetsRequest request) {
        return send(request, new TypeReference<GetAssetsResponse>() {});
    }

    @Override
    public TradeResultVo<SubmitResponse> submit(SubmitRequest request) {
        return send(request, new TypeReference<SubmitResponse>() {});
    }

    @Override
    public TradeResultVo<RevokeResponse> revoke(RevokeRequest request) {
        return send(request, new TypeReference<RevokeResponse>() {});
    }

    @Override
    public TradeResultVo<GetStockListResponse> getStockList(GetStockListRequest request) {
        return send(request, new TypeReference<GetStockListResponse>() {});
    }

    @Override
    public TradeResultVo<GetOrderDataResponse> getOrderData(GetOrderDataRequest request) {
        return send(request, new TypeReference<GetOrderDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetDealDataResponse> getDealData(GetDealDataRequest request) {
        return send(request, new TypeReference<GetDealDataResponse>() {});
    }

    public abstract <T> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType);

}
