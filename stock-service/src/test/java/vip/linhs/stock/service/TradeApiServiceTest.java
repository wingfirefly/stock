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
import vip.linhs.stock.api.request.CrSubmitBatTradeV2Request.CrSubmitData;
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
import vip.linhs.stock.api.request.SubmitBatTradeV2Request.SubmitData;
import vip.linhs.stock.api.request.SubmitRequest;
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
import vip.linhs.stock.util.StockUtil;

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
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testSubmit() {
        SubmitRequest request = new SubmitRequest(TradeApiServiceTest.UserId);
        request.setAmount(100);
        request.setPrice(1.036);
        request.setTradeType(SubmitRequest.B);
        request.setStockCode("588000");
        request.setZqmc("科创50ETF");
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));
        TradeResultVo<SubmitResponse> tradeResultVo = tradeApiService.submit(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testRevoke() {
        RevokeRequest request = new RevokeRequest(TradeApiServiceTest.UserId);
        String wtbh = "2992";
        request.setRevokes(DateFormatUtils.format(new Date(), "yyyy-MM-dd_") + wtbh);
        TradeResultVo<RevokeResponse> tradeResultVo = tradeApiService.revoke(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetStockList() {
        GetStockListRequest request = new GetStockListRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetStockListResponse> tradeResultVo = tradeApiService.getStockList(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetDealData() {
        GetDealDataRequest request = new GetDealDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetDealDataResponse> tradeResultVo = tradeApiService.getDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetOrdersData() {
        GetOrdersDataRequest request = new GetOrdersDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetOrdersDataResponse> tradeResultVo = tradeApiService.getOrdersData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetHisDealData() {
        GetHisDealDataRequest request = new GetHisDealDataRequest(TradeApiServiceTest.UserId);
        Date et = new Date();
        request.setEt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        TradeResultVo<GetHisDealDataResponse> tradeResultVo = tradeApiService.getHisDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetHisOrdersData() {
        GetHisOrdersDataRequest request = new GetHisOrdersDataRequest(TradeApiServiceTest.UserId);
        Date et = new Date();
        request.setEt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        TradeResultVo<GetHisOrdersDataResponse> tradeResultVo = tradeApiService.getHisOrdersData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetCanBuyNewStockListV3() {
        TradeResultVo<GetCanBuyNewStockListV3Response> tradeResultVo = getCanBuyStockListV3ResultVo();
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetConvertibleBondListV2() {
        TradeResultVo<GetConvertibleBondListV2Response> tradeResultVo = getGetConvertibleBondListV2ResultVo();
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testSubmitBatTradeV2() {
        TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyResultVo = getCanBuyStockListV3ResultVo();
        System.out.println(JSON.toJSONString(getCanBuyResultVo));
        Assertions.assertTrue(getCanBuyResultVo.success());
        Assertions.assertFalse(getCanBuyResultVo.getData().isEmpty());

        GetCanBuyNewStockListV3Response getCanBuyResponse = getCanBuyResultVo.getData().get(0);

        List<SubmitData> newStockList = getCanBuyResponse.getNewStockList().stream().map(newStock -> {
            NewQuotaInfo newQuotaInfo = getCanBuyResponse.getNewQuota().stream().filter(v -> v.getMarket().equals(newStock.getMarket())).findAny().orElse(null);
            SubmitData submitData = new SubmitData();
            submitData.setAmount(Integer.min(Integer.parseInt(newStock.getKsgsx()), Integer.parseInt(newQuotaInfo.getKsgsz())));
            submitData.setMarket(newStock.getMarket());
            submitData.setPrice(newStock.getFxj());
            submitData.setStockCode(newStock.getSgdm());
            submitData.setStockName(newStock.getZqmc());
            submitData.setTradeType(SubmitRequest.B);
            return submitData;
        }).collect(Collectors.toList());

        TradeResultVo<GetConvertibleBondListV2Response> getConvertibleBondResultVo = getGetConvertibleBondListV2ResultVo();
        System.out.println(JSON.toJSONString(getConvertibleBondResultVo));
        Assertions.assertTrue(getConvertibleBondResultVo.success());
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
        Assertions.assertTrue(tradeResultVo.success());
    }

    private TradeResultVo<GetCanBuyNewStockListV3Response> getCanBuyStockListV3ResultVo() {
        GetCanBuyNewStockListV3Request request = new GetCanBuyNewStockListV3Request(TradeApiServiceTest.UserId);
        return tradeApiService.getCanBuyNewStockListV3(request);
    }

    private TradeResultVo<GetConvertibleBondListV2Response> getGetConvertibleBondListV2ResultVo() {
        GetConvertibleBondListV2Request request = new GetConvertibleBondListV2Request(TradeApiServiceTest.UserId);
        return tradeApiService.getConvertibleBondListV2(request);
    }

    @Test
    public void testGetCrRzrqAsserts() {
        CrGetRzrqAssertsRequest request = new CrGetRzrqAssertsRequest(TradeApiServiceTest.UserId);
        TradeResultVo<CrGetRzrqAssertsResponse> tradeResultVo = tradeApiService.crGetRzrqAsserts(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetCrQueryCollateral() {
        CrQueryCollateralRequest request = new CrQueryCollateralRequest(TradeApiServiceTest.UserId);
        TradeResultVo<CrQueryCollateralResponse> tradeResultVo = tradeApiService.crQueryCollateral(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testGetCrSubmit() {
        CrSubmitRequest request = new CrSubmitRequest(TradeApiServiceTest.UserId);
        request.setStockCode("588000");
        request.setStockName("科创50ETF");
        request.setPrice(1.036);
        request.setAmount(100);
        request.setTradeInfo(CrSubmitRequest.xyjylx_rz_b);
        request.setMarket(StockUtil.getStockMarket(request.getStockCode()));

        TradeResultVo<CrSubmitResponse> tradeResultVo = tradeApiService.crSubmit(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrRevoke() {
        CrRevokeRequest request = new CrRevokeRequest(TradeApiServiceTest.UserId);
        String wtbh = "2992";
        request.setRevokes(DateFormatUtils.format(new Date(), "yyyy-MM-dd_") + wtbh);
        TradeResultVo<CrRevokeResponse> tradeResultVo = tradeApiService.crRevoke(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrGetOrdersData() {
        CrGetOrdersDataRequest request = new CrGetOrdersDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<CrGetOrdersDataResponse> tradeResultVo = tradeApiService.crGetOrdersData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrGetDealData() {
        CrGetDealDataRequest request = new CrGetDealDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<CrGetDealDataResponse> tradeResultVo = tradeApiService.crGetDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrGetHisDealData() {
        CrGetHisDealDataRequest request = new CrGetHisDealDataRequest(TradeApiServiceTest.UserId);
        Date et = new Date();
        request.setEt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        TradeResultVo<CrGetHisDealDataResponse> tradeResultVo = tradeApiService.crGetHisDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrGetHisOrdersData() {
        CrGetHisOrdersDataRequest request = new CrGetHisOrdersDataRequest(TradeApiServiceTest.UserId);
        Date et = new Date();
        request.setEt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateFormatUtils.format(et, "yyyy-MM-dd"));
        TradeResultVo<CrGetHisOrdersDataResponse> tradeResultVo = tradeApiService.crGetHisOrdersData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrGetCanBuyNewStockListV3() {
        TradeResultVo<CrGetCanBuyNewStockListV3Response> tradeResultVo = crGetCanBuyStockListV3ResultVo();
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrGetConvertibleBondListV2() {
        TradeResultVo<CrGetConvertibleBondListV2Response> tradeResultVo = crGetConvertibleBondListV2ResultVo();
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    @Test
    public void testCrSubmitBatTradeV2() {
        TradeResultVo<CrGetCanBuyNewStockListV3Response> getCanBuyResultVo = crGetCanBuyStockListV3ResultVo();
        System.out.println(JSON.toJSONString(getCanBuyResultVo));
        Assertions.assertTrue(getCanBuyResultVo.success());
        Assertions.assertFalse(getCanBuyResultVo.getData().isEmpty());

        CrGetCanBuyNewStockListV3Response getCanBuyResponse = getCanBuyResultVo.getData().get(0);

        List<SubmitData> newStockList = getCanBuyResponse.getNewStockList().stream().map(newStock -> {
            CrGetCanBuyNewStockListV3Response.NewQuotaInfo newQuotaInfo = getCanBuyResponse.getNewQuota().stream().filter(v -> v.getMarket().equals(newStock.getMarket())).findAny().orElse(null);
            CrSubmitData submitData = new CrSubmitData();
            submitData.setAmount(Integer.min(Integer.parseInt(newStock.getKsgsx()), Integer.parseInt(newQuotaInfo.getCustQuota())));
            submitData.setMarket(newStock.getMarket());
            submitData.setPrice(newStock.getFxj());
            submitData.setStockCode(newStock.getSgdm());
            submitData.setStockName(newStock.getZqmc());
            submitData.setTradeType(SubmitRequest.B);
            return submitData;
        }).collect(Collectors.toList());

        TradeResultVo<CrGetConvertibleBondListV2Response> getConvertibleBondResultVo = crGetConvertibleBondListV2ResultVo();
        System.out.println(JSON.toJSONString(getConvertibleBondResultVo));
        Assertions.assertTrue(getConvertibleBondResultVo.success());
        Assertions.assertFalse(getConvertibleBondResultVo.getData().isEmpty());

        List<SubmitData> convertibleBondList = getConvertibleBondResultVo.getData().stream().map(convertibleBond -> {
            CrSubmitData submitData = new CrSubmitData();
            submitData.setAmount(Integer.parseInt(convertibleBond.getLIMITBUYVOL()));
            submitData.setMarket(convertibleBond.getMarket());
            submitData.setPrice(convertibleBond.getPARVALUE());
            submitData.setStockCode(convertibleBond.getSUBCODE());
            submitData.setStockName(convertibleBond.getBONDNAME());
            submitData.setTradeType(SubmitRequest.B);
            return submitData;
        }).collect(Collectors.toList());

        newStockList.addAll(convertibleBondList);

        CrSubmitBatTradeV2Request request = new CrSubmitBatTradeV2Request(TradeApiServiceTest.UserId);
        request.setList(newStockList);

        TradeResultVo<CrSubmitBatTradeV2Response> tradeResultVo = tradeApiService.crSubmitBatTradeV2(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assertions.assertTrue(tradeResultVo.success());
    }

    private TradeResultVo<CrGetCanBuyNewStockListV3Response> crGetCanBuyStockListV3ResultVo() {
        CrGetCanBuyNewStockListV3Request request = new CrGetCanBuyNewStockListV3Request(TradeApiServiceTest.UserId);
        return tradeApiService.crGetCanBuyNewStockListV3(request);
    }

    private TradeResultVo<CrGetConvertibleBondListV2Response> crGetConvertibleBondListV2ResultVo() {
        CrGetConvertibleBondListV2Request request = new CrGetConvertibleBondListV2Request(TradeApiServiceTest.UserId);
        return tradeApiService.crGetConvertibleBondListV2(request);
    }

}
