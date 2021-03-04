package vip.linhs.stock.trategy.handle;

import vip.linhs.stock.model.vo.trade.TradeRuleVo;
import vip.linhs.stock.trategy.model.BaseStrategyInput;

public abstract class BaseStrategyHandler<I extends BaseStrategyInput, R> implements StrategyHandler {

    @Override
    public void handle(TradeRuleVo tradeRuleVo) {
        I input = queryInput(tradeRuleVo);
        R result = handle(input);
        handleResult(input, result);
    }

    public abstract I queryInput(TradeRuleVo tradeRuleVo);

    public abstract R handle(I input);

    public abstract void handleResult(I input, R result);

}
