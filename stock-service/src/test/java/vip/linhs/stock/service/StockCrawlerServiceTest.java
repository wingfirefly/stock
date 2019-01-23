package vip.linhs.stock.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.po.StockInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockCrawlerServiceTest {

    @Autowired
    private StockCrawlerService stockCrawlerService;

    @Test
    public void testGetStockList() {
        List<StockInfo> stockList = stockCrawlerService.getStockList();
        Assert.assertNotNull(stockList);
        Assert.assertFalse(stockList.isEmpty());
    }

    @Test
    public void testGetDailyIndex() {
        DailyIndex dailyIndex = stockCrawlerService.getDailyIndex("000001");
        Assert.assertNotNull(dailyIndex);

        dailyIndex = stockCrawlerService.getDailyIndex("sz000001");
        Assert.assertNotNull(dailyIndex);

        dailyIndex = stockCrawlerService.getDailyIndex("sh000001");
        Assert.assertNotNull(dailyIndex);
    }

    @Test
    public void testGetHistoryDailyIndexs() {
        List<DailyIndex> list = stockCrawlerService.getHistoryDailyIndexs("300542");
        Assert.assertFalse(list.isEmpty());
    }

}
