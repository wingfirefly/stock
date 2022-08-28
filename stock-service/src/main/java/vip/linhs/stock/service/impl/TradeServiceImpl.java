package vip.linhs.stock.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.api.response.CrGetDealDataResponse;
import vip.linhs.stock.api.response.CrGetOrdersDataResponse;
import vip.linhs.stock.api.response.CrQueryCollateralResponse;
import vip.linhs.stock.api.response.GetDealDataResponse;
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

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_USER_LIST, key = "'all'", unless="#result.size() == 0")
    @Override
    public List<TradeUser> getTradeUserList() {
        return tradeUserDao.getList();
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#id.toString()", unless="#result == null")
    @Override
    public TradeUser getTradeUserById(int id) {
        TradeUser tradeUser = tradeUserDao.getById(id);
        if (tradeUser != null && "资金账号".equals(tradeUser.getAccountId()))
            return null;
        return tradeUser;
    }

    @CacheEvict(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#tradeUser.id.toString()")
    @Override
    public void updateTradeUser(TradeUser tradeUser) {
        tradeUserDao.update(tradeUser);
    }

    @Override
    public List<DealVo> getTradeDealList(List<? extends GetDealDataResponse> data) {
        if (data.isEmpty()) {
            return Collections.emptyList();
        }

        return data.stream().map(v -> {
            DealVo dealVo = new DealVo();
            dealVo.setTradeCode(v.getCjbh());
            dealVo.setPrice(v.getCjjg());
            dealVo.setTradeTime(v.getFormatDealTime());
            dealVo.setTradeDate(v.getFormatDealTime());
            dealVo.setTradeType(v.getMmlb());
            dealVo.setVolume(v.getCjsl());
            dealVo.setEntrustCode(v.getWtbh());
            dealVo.setStockCode(v.getZqdm());

            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            dealVo.setStockName(v.getZqmc());
            dealVo.setAbbreviation(stockInfo.getAbbreviation());

            if (v instanceof CrGetDealDataResponse) {
                CrGetDealDataResponse crV = (CrGetDealDataResponse) v;
                dealVo.setCrTradeType(crV.getXyjylx());
                dealVo.setTradeType(crV.getMmsm());
                dealVo.setEntrustCode(crV.getWtxh());
            }
            return dealVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StockVo> getTradeStockList(List<GetStockListResponse> stockList) {
        return stockList.stream().map(v -> {
            StockVo stockVo = getStockVo(v.getZqdm(), null);
            stockVo.setName(v.getZqmc());
            stockVo.setTotalVolume(Integer.parseInt(v.getZqsl()));
            stockVo.setPrice(new BigDecimal(v.getZxjg()));
            stockVo.setCostPrice(new BigDecimal(v.getCbjg()));

            stockVo.setAvailableVolume(Integer.parseInt(v.getKysl()));
            stockVo.setProfit(new BigDecimal(v.getLjyk()));
            return stockVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StockVo> getCrTradeStockList(List<CrQueryCollateralResponse> stockList) {
        List<StockVo> list = stockList.stream().map(v -> {
            StockVo stockVo = getStockVo(v.getZqdm(), null);
            stockVo.setName(v.getZqmc());
            stockVo.setAvailableVolume(Integer.parseInt(v.getGfky()));
            stockVo.setTotalVolume(Integer.parseInt(v.getZqsl()));
            stockVo.setPrice(new BigDecimal(v.getZxjg()));
            stockVo.setCostPrice(new BigDecimal(v.getCbjg()));
            stockVo.setProfit(new BigDecimal(v.getFdyk()));
            return stockVo;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<StockVo> getTradeStockListBySelected(List<StockSelected> selectList) {
        List<String> codeList = selectList.stream().map(v -> StockUtil.getFullCode(v.getCode())).collect(Collectors.toList());
        List<DailyIndex> dailyIndexList = stockCrawlerService.getDailyIndex(codeList);
        List<StockVo> list = selectList.stream().map(v -> {
            StockVo stockVo = getStockVo(v.getCode(), dailyIndexList);
            stockVo.setAvailableVolume(0);
            stockVo.setTotalVolume(0);
            stockVo.setCostPrice(BigDecimal.ZERO);
            stockVo.setProfit(BigDecimal.ZERO);
            return stockVo;
        }).collect(Collectors.toList());
        return list;
    }

    private StockVo getStockVo(String stockCode, List<DailyIndex> dailyIndexList) {
        StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(stockCode));
        BigDecimal rate = BigDecimal.ZERO;
        BigDecimal closingPrice = BigDecimal.ZERO;
        if (stockInfo.isValid()) {
            DailyIndex dailyIndex = null;
            if (dailyIndexList == null) {
                dailyIndex = stockCrawlerService.getDailyIndex(stockInfo.getCode());
            } else {
                dailyIndex = dailyIndexList.stream().filter(d -> d.getCode().contains(stockInfo.getCode())).findAny().orElse(null);
            }
            if (dailyIndex != null && DecimalUtil.bg(dailyIndex.getClosingPrice(), BigDecimal.ZERO)) {
                rate = StockUtil.calcIncreaseRate(dailyIndex.getClosingPrice(),
                            dailyIndex.getPreClosingPrice());
                closingPrice = dailyIndex.getClosingPrice();
            }
        }

        StockVo stockVo = new StockVo();
        stockVo.setName(stockInfo.getName());
        stockVo.setAbbreviation(stockInfo.getAbbreviation());
        stockVo.setStockCode(stockInfo.getCode());
        stockVo.setExchange(stockInfo.getExchange());
        stockVo.setRate(rate);
        stockVo.setPrice(closingPrice);
        return stockVo;
    }

    @Override
    public List<OrderVo> getTradeOrderList(List<? extends GetOrdersDataResponse> orderList) {
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
            if (v instanceof CrGetOrdersDataResponse) {
                CrGetOrdersDataResponse crV = (CrGetOrdersDataResponse) v;
                orderVo.setCrTradeType(crV.getXyjylbbz());
                orderVo.setTradeType(crV.getMmsm());
            }
            return orderVo;
        }).collect(Collectors.toList());
        return list;
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
    public List<TradeOrder> getLastTradeOrderListByRuleId(int ruleId, int userId) {
        return tradeOrderDao.getLastListByRuleId(ruleId, userId);
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
