package vip.linhs.stock.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vip.linhs.stock.model.vo.PageParam;

@SpringBootTest
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Test
    public void testGetTradeRuleList() {
        PageParam pageParam = new PageParam();
        tradeService.getTradeRuleList(pageParam);
    }

}
