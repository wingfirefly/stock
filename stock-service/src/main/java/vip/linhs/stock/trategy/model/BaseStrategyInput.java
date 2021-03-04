package vip.linhs.stock.trategy.model;

import vip.linhs.stock.model.vo.trade.TradeRuleVo;

public class BaseStrategyInput {

    private TradeRuleVo tradeRuleVo;

    public BaseStrategyInput(TradeRuleVo tradeRuleVo) {
        this.tradeRuleVo = tradeRuleVo;
    }

    public TradeRuleVo getTradeRuleVo() {
        return tradeRuleVo;
    }

    public void setTradeRuleVo(TradeRuleVo tradeRuleVo) {
        this.tradeRuleVo = tradeRuleVo;
    }

    public int getUserId() {
        return tradeRuleVo.getUserId();
    }

}
