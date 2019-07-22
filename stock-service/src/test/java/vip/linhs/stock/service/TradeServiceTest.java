package vip.linhs.stock.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import vip.linhs.stock.model.po.TradeRule;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Test
    public void testGetTradeRuleByStockCode() {
        TradeRule tradeRule = tradeService.getTradeRuleByStockCode("600368");
        Assert.assertNotNull(tradeRule);
        tradeRule = tradeService.getTradeRuleByStockCode("300542");
        Assert.assertNull(tradeRule);
    }

}
