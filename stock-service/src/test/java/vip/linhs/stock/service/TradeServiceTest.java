package vip.linhs.stock.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vip.linhs.stock.model.po.TradeRule;

@SpringBootTest
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Test
    public void testGetTradeRuleByStockCode() {
        TradeRule tradeRule = tradeService.getTradeRuleByStockCode("600368");
        Assertions.assertNotNull(tradeRule);
        tradeRule = tradeService.getTradeRuleByStockCode("300542");
        Assertions.assertNull(tradeRule);
    }

}
