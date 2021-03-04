package vip.linhs.stock.strategy.handle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.TradeRuleVo;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.trategy.handle.StrategyHandler;

@SpringBootTest
public class GriStrategyHandlerTest {

    @Autowired
    @Qualifier("gridStrategyHandler")
    private StrategyHandler strategyHandler;

    @Autowired
    private TradeService tradeService;

    @Test
    public void testHandle() {
        PageParam pageParam = new PageParam();
        pageParam.setStart(0);
        pageParam.setLength(Integer.MAX_VALUE);
        PageVo<TradeRuleVo>  pageVo = tradeService.getTradeRuleList(pageParam);
        TradeRuleVo tradeRuleVo = pageVo.getData().stream().filter(v -> v.getStrategyId() == 1).findAny().get();
        if (tradeRuleVo.isValid()) {
            strategyHandler.handle(tradeRuleVo);
        }
    }

}
