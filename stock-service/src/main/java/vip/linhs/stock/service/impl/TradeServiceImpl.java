package vip.linhs.stock.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.GetDealDataResponse;
import vip.linhs.stock.api.response.GetOrdersDataResponse;
import vip.linhs.stock.api.response.GetStockListResponse;
import vip.linhs.stock.dao.TradeMethodDao;
import vip.linhs.stock.dao.TradeOrderDao;
import vip.linhs.stock.dao.TradeRuleDao;
import vip.linhs.stock.dao.TradeStockInfoRuleDao;
import vip.linhs.stock.dao.TradeUserDao;
import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeOrder;
import vip.linhs.stock.model.po.TradeRule;
import vip.linhs.stock.model.po.TradeStockInfoRule;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.model.vo.trade.DealVo;
import vip.linhs.stock.model.vo.trade.OrderVo;
import vip.linhs.stock.model.vo.trade.StockVo;
import vip.linhs.stock.model.vo.trade.TradeConfigVo;
import vip.linhs.stock.service.StockService;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.StockConsts;
import vip.linhs.stock.util.StockUtil;

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
    private TradeStockInfoRuleDao tradeStockInfoRuleDao;

    @Autowired
    private StockService stockService;

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_METHOD, key = "#name")
    @Override
    public TradeMethod getTradeMethodByName(String name) {
        return tradeMethodDao.getByName(name);
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#id")
    @Override
    public TradeUser getTradeById(int id) {
        return tradeUserDao.getById(id);
    }

    @CacheEvict(value = StockConsts.CACHE_KEY_TRADE_USER, key = "#tradeUser.id")
    @Override
    public void update(TradeUser tradeUser) {
        tradeUserDao.update(tradeUser);
    }

    @Cacheable(value = StockConsts.CACHE_KEY_TRADE_RULE, key = "#stockCode")
    @Override
    public TradeRule getTradeRuleByStockCode(String stockCode) {
        return tradeRuleDao.getTradeRuleByStockCode(stockCode);
    }

    @Override
    public PageVo<TradeRule> getRuleList(PageParam pageParam) {
        return tradeRuleDao.get(pageParam);
    }

    @Override
    public  PageVo<TradeConfigVo> getConfigList(PageParam pageParam) {
        PageVo<TradeStockInfoRule> pageVo = tradeStockInfoRuleDao.get(pageParam);
        List<TradeStockInfoRule> list = pageVo.getData();
        List<TradeConfigVo> tradeConfigVoList = list.stream().map(tradeStockInfoRule -> {
            TradeConfigVo tradeConfigVo = new TradeConfigVo();
            tradeConfigVo.setCreateTime(tradeStockInfoRule.getCreateTime());
            tradeConfigVo.setId(tradeStockInfoRule.getId());
            tradeConfigVo.setRuleId(tradeStockInfoRule.getRuleId());
            tradeConfigVo.setState(tradeStockInfoRule.getState());
            tradeConfigVo.setStockCode(tradeStockInfoRule.getStockCode());
            String stockName = stockService.getStockByFullCode(StockUtil.getFullCode(tradeStockInfoRule.getStockCode())).getName();
            tradeConfigVo.setStockName(stockName);
            tradeConfigVo.setUpdateTime(tradeStockInfoRule.getUpdateTime());
            return tradeConfigVo;
        }).collect(Collectors.toList());
        return new PageVo<>(tradeConfigVoList, pageVo.getTotalRecords());
    }

    @CacheEvict(value = StockConsts.CACHE_KEY_TRADE_RULE, allEntries = true)
    @Override
    public void changeConfigState(int state, int id) {
        tradeStockInfoRuleDao.updateStateById(state, id);
    }

    @Override
    public List<TradeOrder> getTradeOrderList() {
        return tradeOrderDao.getAll();
    }

    @Override
    public List<TradeOrder> getTodayTradeOrderList() {
        return tradeOrderDao.getListByDate(new Date());
    }

    @Override
    public void saveTradeOrder(TradeOrder tradeOrder) {
        tradeOrderDao.save(tradeOrder);
    }

    @Override
    public List<DealVo> getTradeDealList(List<GetDealDataResponse> data) {
        if (data.isEmpty()) {
            return Collections.emptyList();
        }
        List<TradeOrder> tradeOrderList = getTradeOrderList();
        return data.stream().map(v -> {
            DealVo dealVo = new DealVo();
            dealVo.setTradeCode(v.getCjbh());
            dealVo.setPrice(v.getCjjg());
            dealVo.setTradeTime(new StringBuilder(v.getCjsj()).insert(4, ':').insert(2, ':').toString());
            dealVo.setTradeType(v.getMmlb());
            dealVo.setVolume(v.getCjsl());
            dealVo.setEntrustCode(v.getWtbh());
            dealVo.setStockCode(v.getZqdm());
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            dealVo.setStockName(stockInfo.getName());
            dealVo.setAbbreviation(stockInfo.getAbbreviation());
            tradeOrderList.forEach(vv -> {
                if (v.getCjbh().equals(vv.getTradeCode())) {
                    if (SubmitRequest.S.equals(vv.getTradeType())) {
                        dealVo.setRelatedSaleEntrustCode(vv.getEntrustCode());
                    } else if (SubmitRequest.B.equals(vv.getTradeType())) {
                        dealVo.setRelatedBuyEntrustCode(vv.getEntrustCode());
                    }
                }
            });
            return dealVo;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteTradeCode(String tradeCode, String tradeType) {
        tradeOrderDao.delete(tradeCode, tradeType);
    }

    @Override
    public List<StockVo> getTradeStockList(List<GetStockListResponse> stockList) {
        List<StockVo> list = stockList.stream().filter(v -> StockUtil.isStockCode(v.getZqdm())).map(v -> {
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            StockVo stockVo = new StockVo();
            stockVo.setAbbreviation(stockInfo.getAbbreviation());
            stockVo.setStockCode(stockInfo.getCode());
            stockVo.setExchange(stockInfo.getExchange());
            stockVo.setName(stockInfo.getName());
            stockVo.setAvailableVolume(Integer.parseInt(v.getKysl()));
            stockVo.setTotalVolume(Integer.parseInt(v.getZqsl()));
            stockVo.setPrice(new BigDecimal(v.getZxjg()));
            return stockVo;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<OrderVo> getTradeOrderList(List<GetOrdersDataResponse> orderList) {
        List<OrderVo> list = orderList.stream().filter(v -> StockUtil.isStockCode(v.getZqdm())).map(v -> {
            StockInfo stockInfo = stockService.getStockByFullCode(StockUtil.getFullCode(v.getZqdm()));
            OrderVo orderVo = new OrderVo();
            orderVo.setAbbreviation(stockInfo.getAbbreviation());
            orderVo.setEnsuerTime(v.getWtsj());
            orderVo.setEntrustCode(v.getWtbh());
            orderVo.setPrice(new BigDecimal(v.getWtjg()));
            orderVo.setState(v.getWtzt());
            orderVo.setStockCode(v.getZqdm());
            orderVo.setStockName(stockInfo.getName());
            orderVo.setTradeType(v.getMmlb());
            orderVo.setVolume(Integer.parseInt(v.getWtsl()));
            return orderVo;
        }).collect(Collectors.toList());
        return list;
    }

}
