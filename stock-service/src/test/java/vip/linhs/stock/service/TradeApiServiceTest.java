package vip.linhs.stock.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.api.TradeResultVo;
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
import vip.linhs.stock.api.request.SubmitBatTradeV2Request.SubmitData;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.GetAssetsResponse;
import vip.linhs.stock.api.response.GetCanBuyNewStockListV3Response;
import vip.linhs.stock.api.response.GetCanBuyNewStockListV3Response.NewQuotaInfo;
import vip.linhs.stock.api.response.GetConvertibleBondListV2Response;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetHisOrdersDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitBatTradeV2Response;
import vip.linhs.stock.api.response.SubmitResponse;

@SpringBootTest
public class TradeApiServiceTest {

    private static final int UserId = 1;

    @Autowired
    private TradeApiService tradeApiService;

    @Test
    public void testGetAsserts() {
        GetAssetsRequest request = new GetAssetsRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetAssetsResponse> tradeResultVo = tradeApiService.getAsserts(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testSubmit() {
        SubmitRequest request = new SubmitRequest(TradeApiServiceTest.UserId);
        request.setAmount(100);
        request.setPrice(1.291);
        request.setTradeType(SubmitRequest.B);
        request.setStockCode("588000");
        request.setZqmc("科创50ETF");
        TradeResultVo<SubmitResponse> tradeResultVo = tradeApiService.submit(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testRevoke() {
        RevokeRequest request = new RevokeRequest(TradeApiServiceTest.UserId);
        request.setRevokes("20190527_299");
        TradeResultVo<RevokeResponse> tradeResultVo = tradeApiService.revoke(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetStockList() {
        GetStockListRequest request = new GetStockListRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetStockListResponse> tradeResultVo = tradeApiService.getStockList(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetDealData() {
        GetDealDataRequest request = new GetDealDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetDealDataResponse> tradeResultVo = tradeApiService.getDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetOrdersData() {
        GetOrdersDataRequest request = new GetOrdersDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetOrdersDataResponse> tradeResultVo = tradeApiService.getOrdersData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetHisDealData() {
        GetHisDealDataRequest request = new GetHisDealDataRequest(TradeApiServiceTest.UserId);
        request.setEt(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        TradeResultVo<GetHisDealDataResponse> tradeResultVo = tradeApiService.getHisDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetHisOrdersData() {
        GetHisOrdersDataRequest request = new GetHisOrdersDataRequest(TradeApiServiceTest.UserId);
        request.setEt(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 2 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        TradeResultVo<GetHisOrdersDataResponse> tradeResultVo = tradeApiService.getHisOrdersData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetCanBuyNewStockListV3() {
        TradeResultVo<GetCanBuyNewStockListV3Response> tradeResultVo = getCanBuyStockListV3ResultVo();
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetConvertibleBondListV2() {
        TradeResultVo<GetConvertibleBondListV2Response> tradeResultVo = getGetConvertibleBondListV2ResultVo();
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testSubmitBatTradeV2() {
        TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyResultVo = getCanBuyStockListV3ResultVo();
        System.out.println(JSON.toJSONString(getCanBuyResultVo));
        Assertions.assertTrue(getCanBuyResultVo.isSuccess());
        Assertions.assertFalse(getCanBuyResultVo.getData().isEmpty());

        GetCanBuyNewStockListV3Response getCanBuyResponse = getCanBuyResultVo.getData().get(0);

        List<SubmitData> newStockList = getCanBuyResponse.getNewStockList().stream().map(newStock -> {
            NewQuotaInfo newQuotaInfo = getCanBuyResponse.getNewQuota().stream().filter(v -> v.getMarket().equals(newStock.getMarket())).findAny().orElseGet(null);
            SubmitData submitData = new SubmitData();
            submitData.setAmount(Integer.max(Integer.parseInt(newStock.getKsgsx()), Integer.parseInt(newQuotaInfo.getKsgsz())));
            submitData.setMarket(newStock.getMarket());
            submitData.setPrice(newStock.getFxj());
            submitData.setStockCode(newStock.getSgdm());
            submitData.setStockName(newStock.getZqmc());
            submitData.setTradeType(SubmitRequest.B);
            return submitData;
       }).collect(Collectors.toList());

        TradeResultVo<GetConvertibleBondListV2Response> getConvertibleBondResultVo = getGetConvertibleBondListV2ResultVo();
        System.out.println(JSON.toJSONString(getConvertibleBondResultVo));
        Assertions.assertTrue(getConvertibleBondResultVo.isSuccess());
        Assertions.assertFalse(getConvertibleBondResultVo.getData().isEmpty());

        List<SubmitData> convertibleBondList = getConvertibleBondResultVo.getData().stream().map(convertibleBond -> {
            SubmitData submitData = new SubmitData();
            submitData.setAmount(Integer.parseInt(convertibleBond.getLIMITBUYVOL()));
            submitData.setMarket(convertibleBond.getMarket());
            submitData.setPrice(convertibleBond.getPARVALUE());
            submitData.setStockCode(convertibleBond.getSUBCODE());
            submitData.setStockName(convertibleBond.getBONDNAME());
            submitData.setTradeType(SubmitRequest.B);
            return submitData;
       }).collect(Collectors.toList());

        newStockList.addAll(convertibleBondList);

        SubmitBatTradeV2Request request = new SubmitBatTradeV2Request(TradeApiServiceTest.UserId);
        request.setList(newStockList);

        TradeResultVo<SubmitBatTradeV2Response> tradeResultVo = tradeApiService.submitBatTradeV2(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.isSuccess());
    }

    private TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyStockListV3ResultVo() {
        GetCanBuyNewStockListV3Request request = new GetCanBuyNewStockListV3Request(TradeApiServiceTest.UserId);
        return tradeApiService.getCanBuyNewStockListV3(request);
    }

    private TradeResultVo<GetConvertibleBondListV2Response> getGetConvertibleBondListV2ResultVo() {
        GetConvertibleBondListV2Request request = new GetConvertibleBondListV2Request(TradeApiServiceTest.UserId);
        return tradeApiService.getConvertibleBondListV2(request);
    }

}
