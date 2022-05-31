package vip.linhs.stock.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetHisDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.dao.*;
import vip.linhs.stock.model.po.*;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeRuleVo;
import vip.linhs.stock.service.StockCrawlerService;
import vip.linhs.stock.service.StockService;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.DecimalUtil;
import vip.linhs.stock.util.StockConsts;
import vip.linhs.stock.util.StockUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMethodDao tradeMethodDao;

    @Autowired
    private TradeUserDao tradeUserDao;

    @Autowired
    private TradeRuleDao tradeRuleDao;

    @Autowired
    private TradeOrderDao tradeOrderDao;

    @Autowired
    private TradeStrategyDao tradeStrategyDao;

    @Autowired
    private TradeDealDao tradeDealDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockCrawlerService stockCrawlerService;

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_METHOD, key = "#name", unless="#result == null")
    @Override
    public TradeMethod getTradeMethodByName(String name) {
        return tradeMethodDao.getByName(name);
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#id", unless="#result == null")
    @Override
    public TradeUser getTradeUserById(int id) {
        TradeUser tradeUser = tradeUserDao.getById(id);
        if (tradeUser != null && "资金账号".equals(tradeUser.getAccountId()))
            return null;
        return tradeUser;
    }

    @CacheEvict(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#tradeUser.id")
    @Override
    public void updateTradeUser(TradeUser tradeUser) {
        tradeUserDao.update(tradeUser);
    }

    @Override
    public List<DealVo> getTradeDealList(List<GetDealDataResponse> data) {
        if (data.isEmpty()) {
            return Collections.emptyList();
        }
        return data.stream().map(v -> {
            DealVo dealVo = new DealVo();
            dealVo.setTradeCode(v.getCjbh());
            dealVo.setPrice(v.getCjjg());
            dealVo.setTradeTime(v.getFormatDealTime());
            dealVo.setTradeType(v.getMmlb());
            dealVo.setVolume(v.getCjsl());
            dealVo.setEntrustCode(v.getWtbh());
            dealVo.setStockCode(v.getZqdm());
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            dealVo.setStockName(v.getZqmc());
            dealVo.setAbbreviation(stockInfo.getAbbreviation());
            return dealVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StockVo> getTradeStockList(List<GetStockListResponse> stockList) {
        List<StockVo> list = stockList.stream().map(v -> {
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            BigDecimal rate = BigDecimal.ZERO;
            if (stockInfo.isValid()) {
                DailyIndex dailyIndex = stockCrawlerService.getDailyIndex(stockInfo.getCode());
                if (dailyIndex != null && DecimalUtil.bg(dailyIndex.getClosingPrice(), BigDecimal.ZERO)) {
                    rate = StockUtil.calcIncreaseRate(dailyIndex.getClosingPrice(),
                            dailyIndex.getPreClosingPrice());
                }
            }

            StockVo stockVo = new StockVo();
            stockVo.setAbbreviation(stockInfo.getAbbreviation());
            stockVo.setStockCode(stockInfo.getCode());
            stockVo.setExchange(stockInfo.getExchange());
            stockVo.setName(v.getZqmc());
            stockVo.setAvailableVolume(Integer.parseInt(v.getKysl()));
            stockVo.setTotalVolume(Integer.parseInt(v.getZqsl()));
            stockVo.setPrice(new BigDecimal(v.getZxjg()));
            stockVo.setCostPrice(new BigDecimal(v.getCbjg()));
            stockVo.setProfit(new BigDecimal(v.getLjyk()));
            stockVo.setRate(rate);
            return stockVo;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<StockVo> getTradeStockListBySelected(List<StockSelected> selectList) {
        List<String> codeList = selectList.stream().map(v -> StockUtil.getFullCode(v.getCode())).collect(Collectors.toList());
        List<DailyIndex> dailyIndexList = stockCrawlerService.getDailyIndex(codeList);

        List<StockVo> list = selectList.stream().map(v -> {
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getCode()));
            StockVo stockVo = new StockVo();
            stockVo.setAbbreviation(stockInfo.getAbbreviation());
            stockVo.setStockCode(stockInfo.getCode());
            stockVo.setExchange(stockInfo.getExchange());
            stockVo.setName(stockInfo.getName());
            stockVo.setAvailableVolume(0);
            stockVo.setTotalVolume(0);

            BigDecimal rate = BigDecimal.ZERO;
            BigDecimal closingPrice = BigDecimal.ZERO;

            DailyIndex dailyIndex = dailyIndexList.stream().filter(d -> d.getCode().contains(v.getCode())).findAny().orElse(null);
            if (dailyIndex != null) {
                closingPrice = dailyIndex.getClosingPrice();
                if (DecimalUtil.bg(dailyIndex.getClosingPrice(), BigDecimal.ZERO)) {
                    rate = StockUtil.calcIncreaseRate(dailyIndex.getClosingPrice(),
                            dailyIndex.getPreClosingPrice());
                }
            }

            stockVo.setCostPrice(BigDecimal.ZERO);
            stockVo.setProfit(BigDecimal.ZERO);
            stockVo.setRate(rate);
            stockVo.setPrice(closingPrice);
            return stockVo;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<OrderVo> getTradeOrderList(List<GetOrdersDataResponse> orderList) {
        List<OrderVo> list = orderList.stream().map(v -> {
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            OrderVo orderVo = new OrderVo();
            orderVo.setAbbreviation(stockInfo.getAbbreviation());
            orderVo.setEnsuerTime(v.getWtsj());
            orderVo.setEntrustCode(v.getWtbh());
            orderVo.setPrice(new BigDecimal(v.getWtjg()));
            orderVo.setState(v.getWtzt());
            orderVo.setStockCode(v.getZqdm());
            orderVo.setStockName(v.getZqmc());
            orderVo.setTradeType(v.getMmlb());
            orderVo.setVolume(Integer.parseInt(v.getWtsl()));
            return orderVo;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<DealVo> getTradeHisDealList(List<GetHisDealDataResponse> data) {
        if (data.isEmpty()) {
            return Collections.emptyList();
        }
        return data.stream().map(v -> {
            DealVo dealVo = new DealVo();
            dealVo.setTradeCode(v.getCjbh());
            dealVo.setPrice(v.getCjjg());
            dealVo.setTradeDate(new StringBuilder(v.getCjrq()).insert(6, '-').insert(4, '-').toString());
            if (v.getCjsj().length() == 6) {
                dealVo.setTradeTime(new StringBuilder(v.getCjsj()).insert(4, ':').insert(2, ':').toString());
            } else {
                dealVo.setTradeTime("00:00:00");
            }
            dealVo.setTradeType(v.getMmlb());
            dealVo.setVolume(v.getCjsl());
            dealVo.setEntrustCode(v.getWtbh());
            dealVo.setStockCode(v.getZqdm());
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            dealVo.setStockName(v.getZqmc());
            dealVo.setAbbreviation(stockInfo.getAbbreviation());
            return dealVo;
        }).collect(Collectors.toList());
    }

    @Override
    public PageVo<TradeRuleVo> getTradeRuleList(PageParam pageParam) {
        PageVo<TradeRule> pageVo = tradeRuleDao.get(pageParam);

        List<TradeStrategy> strategyList = tradeStrategyDao.getAll();
        List<TradeRule> list = pageVo.getData();

        List<TradeRuleVo> tradeConfigVoList = list.stream().map(tradeRule -> {
            TradeRuleVo tradeRuleVo = new TradeRuleVo();
            BeanUtils.copyProperties(tradeRule, tradeRuleVo);

            String stockName = stockService.getStockByFullCode(StockUtil.getFullCode(tradeRuleVo.getStockCode())).getName();
            TradeStrategy tradeStrategy = strategyList.stream().filter(v -> v.getId() == tradeRuleVo.getStrategyId()).findAny().orElse(null);
            String strategyName = tradeStrategy.getName();
            String strategyBeanName = tradeStrategy.getBeanName();

            tradeRuleVo.setStockName(stockName);
            tradeRuleVo.setStrategyName(strategyName);
            tradeRuleVo.setStrategyBeanName(strategyBeanName);
            return tradeRuleVo;
        }).collect(Collectors.toList());
        return new PageVo<>(tradeConfigVoList, pageVo.getTotalRecords());
    }

    @Override
    public void changeTradeRuleState(int state, int id) {
        tradeRuleDao.updateState(state, id);
    }

    @Override
    public List<TradeOrder> getLastTradeOrderListByRuleId(int ruleId) {
        return tradeOrderDao.getLastListByRuleId(ruleId);
    }

    @Override
    public void saveTradeOrderList(List<TradeOrder> tradeOrderList) {
        for (TradeOrder tradeOrder : tradeOrderList) {
            if (tradeOrder.getId() > 0) {
                tradeOrderDao.update(tradeOrder);
            } else {
                tradeOrderDao.add(tradeOrder);
            }
        }
    }

    @Override
    public void resetRule(int id) {
        tradeOrderDao.setInvalidByRuleId(id);
    }

    @Override
    public void saveTradeDealList(List<TradeDeal> list) {
        list.forEach(tradeDealDao::add);
    }

    @Override
    public List<TradeDeal> getTradeDealListByDate(Date date) {
        return tradeDealDao.getByDate(date);
    }

}
