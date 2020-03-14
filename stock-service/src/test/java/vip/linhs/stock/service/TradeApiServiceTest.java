package vip.linhs.stock.service;

import java.util.Date;

import org.apache.http.client.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.GetAssetsRequest;
import vip.linhs.stock.api.request.GetDealDataRequest;
import vip.linhs.stock.api.request.GetHisDealDataRequest;
import vip.linhs.stock.api.request.GetOrderDataRequest;
import vip.linhs.stock.api.request.GetStockListRequest;
import vip.linhs.stock.api.request.RevokeRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.GetAssetsResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetOrderDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.api.response.RevokeResponse;
import vip.linhs.stock.api.response.SubmitResponse;

@RunWith(SpringRunner.class)
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
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testSubmit() {
        SubmitRequest request = new SubmitRequest(TradeApiServiceTest.UserId);
        request.setAmount(100);
        request.setPrice(41);
        request.setTradeType(SubmitRequest.B);
        request.setStockCode("300542");
        TradeResultVo<SubmitResponse> tradeResultVo = tradeApiService.submit(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testRevoke() {
        RevokeRequest request = new RevokeRequest(TradeApiServiceTest.UserId);
        request.setRevokes("20190527_299");
        TradeResultVo<RevokeResponse> tradeResultVo = tradeApiService.revoke(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetStockList() {
        GetStockListRequest request = new GetStockListRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetStockListResponse> tradeResultVo = tradeApiService.getStockList(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetDealData() {
        GetDealDataRequest request = new GetDealDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetDealDataResponse> tradeResultVo = tradeApiService.getDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetOrderData() {
        GetOrderDataRequest request = new GetOrderDataRequest(TradeApiServiceTest.UserId);
        TradeResultVo<GetOrderDataResponse> tradeResultVo = tradeApiService.getOrderData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

    @Test
    public void testGetHisDealData() {
        GetHisDealDataRequest request = new GetHisDealDataRequest(TradeApiServiceTest.UserId);
        request.setEt(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 2 * 24 * 3600 * 1000);
        request.setSt(DateUtils.formatDate(et, "yyyy-MM-dd"));
        TradeResultVo<GetHisDealDataResponse> tradeResultVo = tradeApiService.getHisDealData(request);
        System.out.println(JSON.toJSONString(tradeResultVo));
        Assert.assertTrue(tradeResultVo.isSuccess());
    }

}
