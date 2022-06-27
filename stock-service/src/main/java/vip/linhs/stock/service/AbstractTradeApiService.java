package vip.linhs.stock.service;

import com.alibaba.fastjson.TypeReference;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.BaseTradeRequest;
import vip.linhs.stock.api.request.CrGetCanBuyNewStockListV3Request;
import vip.linhs.stock.api.request.CrGetConvertibleBondListV2Request;
import vip.linhs.stock.api.request.CrGetDealDataRequest;
import vip.linhs.stock.api.request.CrGetHisDealDataRequest;
import vip.linhs.stock.api.request.CrGetHisOrdersDataRequest;
import vip.linhs.stock.api.request.CrGetOrdersDataRequest;
import vip.linhs.stock.api.request.CrGetRzrqAssertsRequest;
import vip.linhs.stock.api.request.CrQueryCollateralRequest;
import vip.linhs.stock.api.request.CrRevokeRequest;
import vip.linhs.stock.api.request.CrSubmitBatTradeV2Request;
import vip.linhs.stock.api.request.CrSubmitRequest;
import vip.linhs.stock.api.request.GetAssetsRequest;
import vip.linhs.stock.api.request.GetCanBuyNewStockListV3Request;
import vip.linhs.stock.api.request.GetConvertibleBondListV2Request;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.GetHisDealDataRequest;
import vip.linhs.stock.api.request.GetHisOrdersDataRequest;
import vip.linhs.stock.api.request.GetOrdersDataRequest;
import vip.linhs.stock.api.request.GetStockListRequest;
import vip.linhs.stock.api.request.RevokeRequest;
import vip.linhs.stock.api.request.SubmitBatTradeV2Request;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.BaseTradeResponse;
import vip.linhs.stock.api.response.CrGetCanBuyNewStockListV3Response;
import vip.linhs.stock.api.response.CrGetConvertibleBondListV2Response;
import vip.linhs.stock.api.response.CrGetDealDataResponse;
import vip.linhs.stock.api.response.CrGetHisDealDataResponse;
import vip.linhs.stock.api.response.CrGetHisOrdersDataResponse;
import vip.linhs.stock.api.response.CrGetOrdersDataResponse;
import vip.linhs.stock.api.response.CrGetRzrqAssertsResponse;
import vip.linhs.stock.api.response.CrQueryCollateralResponse;
import vip.linhs.stock.api.response.CrRevokeResponse;
import vip.linhs.stock.api.response.CrSubmitBatTradeV2Response;
import vip.linhs.stock.api.response.CrSubmitResponse;
import vip.linhs.stock.api.response.GetAssetsResponse;
import vip.linhs.stock.api.response.GetCanBuyNewStockListV3Response;
import vip.linhs.stock.api.response.GetConvertibleBondListV2Response;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetHisOrdersDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitBatTradeV2Response;
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
    public TradeResultVo<GetOrdersDataResponse> getOrdersData(GetOrdersDataRequest request) {
        return send(request, new TypeReference<GetOrdersDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetDealDataResponse> getDealData(GetDealDataRequest request) {
        return send(request, new TypeReference<GetDealDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetHisDealDataResponse> getHisDealData(GetHisDealDataRequest request) {
        return send(request, new TypeReference<GetHisDealDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetHisOrdersDataResponse> getHisOrdersData(GetHisOrdersDataRequest request) {
        return send(request, new TypeReference<GetHisOrdersDataResponse>() {});
    }

    @Override
    public TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyNewStockListV3(GetCanBuyNewStockListV3Request request) {
        return send(request, new TypeReference<GetCanBuyNewStockListV3Response>() {});
    }

    @Override
    public TradeResultVo<GetConvertibleBondListV2Response> getConvertibleBondListV2(GetConvertibleBondListV2Request request) {
        return send(request, new TypeReference<GetConvertibleBondListV2Response>() {});
    }

    @Override
    public TradeResultVo<SubmitBatTradeV2Response> submitBatTradeV2(SubmitBatTradeV2Request request) {
        return send(request, new TypeReference<SubmitBatTradeV2Response>() {});
    }

    @Override
    public TradeResultVo<CrGetRzrqAssertsResponse> crGetRzrqAsserts(CrGetRzrqAssertsRequest request) {
        return send(request, new TypeReference<CrGetRzrqAssertsResponse>() {});
    }

    @Override
    public TradeResultVo<CrQueryCollateralResponse> crQueryCollateral(CrQueryCollateralRequest request) {
        return send(request, new TypeReference<CrQueryCollateralResponse>() {});
    }

    @Override
    public TradeResultVo<CrSubmitResponse> crSubmit(CrSubmitRequest request) {
        return send(request, new TypeReference<CrSubmitResponse>() {});
    }

    @Override
    public TradeResultVo<CrRevokeResponse> crRevoke(CrRevokeRequest request) {
        return send(request, new TypeReference<CrRevokeResponse>() {});
    }

    @Override
    public TradeResultVo<CrGetOrdersDataResponse> crGetOrdersData(CrGetOrdersDataRequest request) {
        return send(request, new TypeReference<CrGetOrdersDataResponse>() {});
    }


    @Override
    public TradeResultVo<CrGetDealDataResponse> crGetDealData(CrGetDealDataRequest request) {
        return send(request, new TypeReference<CrGetDealDataResponse>() {});
    }

    @Override
    public TradeResultVo<CrGetHisDealDataResponse> crGetHisDealData(CrGetHisDealDataRequest request) {
        return send(request, new TypeReference<CrGetHisDealDataResponse>() {});
    }

    @Override
    public TradeResultVo<CrGetHisOrdersDataResponse> crGetHisOrdersData(CrGetHisOrdersDataRequest request) {
        return send(request, new TypeReference<CrGetHisOrdersDataResponse>() {});
    }

    @Override
    public TradeResultVo<CrGetCanBuyNewStockListV3Response> crGetCanBuyNewStockListV3(CrGetCanBuyNewStockListV3Request request) {
        return send(request, new TypeReference<CrGetCanBuyNewStockListV3Response>() {});
    }

    @Override
    public TradeResultVo<CrGetConvertibleBondListV2Response> crGetConvertibleBondListV2(CrGetConvertibleBondListV2Request request) {
        return send(request, new TypeReference<CrGetConvertibleBondListV2Response>() {});
    }

    @Override
    public TradeResultVo<CrSubmitBatTradeV2Response> crSubmitBatTradeV2(CrSubmitBatTradeV2Request request) {
        return send(request, new TypeReference<CrSubmitBatTradeV2Response>() {});
    }

    public abstract <T extends BaseTradeResponse> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType);

}
