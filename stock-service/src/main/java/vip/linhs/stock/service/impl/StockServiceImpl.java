package vip.linhs.stock.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import vip.linhs.stock.dao.DailyIndexDao;
import vip.linhs.stock.dao.StockInfoDao;
import vip.linhs.stock.dao.StockLogDao;
import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.model.po.StockLog;
import vip.linhs.stock.model.vo.DailyIndexVo;
import vip.linhs.stock.model.vo.PageParam;
import vip.linhs.stock.model.vo.PageVo;
import vip.linhs.stock.parser.DailyIndexParser;
import vip.linhs.stock.service.StockCrawlerService;
import vip.linhs.stock.service.StockService;
import vip.linhs.stock.util.StockConsts;
import vip.linhs.stock.util.StockUtil;

@Service
public class StockServiceImpl implements StockService {

    private final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    private static final String LIST_MESSAGE = "'list' must not be null";

    @Autowired
    private StockInfoDao stockInfoDao;

    @Autowired
    private StockLogDao stockLogDao;

    @Autowired
    private DailyIndexDao dailyIndexDao;

    @Autowired
    private StockCrawlerService stockCrawlerService;

    @Autowired
    private DailyIndexParser dailyIndexParser;

    @Override
    public List<StockInfo> getAll() {
        PageParam pageParam = new PageParam();
        pageParam.setStart(0);
        pageParam.setLength(Integer.MAX_VALUE);
        PageVo<StockInfo> pageVo = stockInfoDao.get(pageParam);
        return pageVo.getData();
    }

    @Override
    public List<StockInfo> getAllListed() {
        return getAll().stream().filter(stockInfo ->
            stockInfo.getState() != StockConsts.StockState.Delisted.value()
                && stockInfo.getState() != StockConsts.StockState.Terminated.value()
                && !StockUtil.isCompositeIndex(stockInfo.getExchange(), stockInfo.getCode())
        ).collect(Collectors.toList());
    }

    @Override
    public void addStockLog(List<StockLog> list) {
        Assert.notNull(list, StockServiceImpl.LIST_MESSAGE);
        if (!list.isEmpty()) {
            stockLogDao.add(list);
        }
    }

    @CacheEvict(value = StockConsts.CACHE_KEY_DATA_CODE, allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void update(List<StockInfo> needAddedList, List<StockInfo> needUpdatedList, List<StockLog> stockLogList) {
        if (needAddedList != null) {
            add(needAddedList);
        }
        if (needUpdatedList != null) {
            update(needUpdatedList);
        }
        if (stockLogList != null) {
            addStockLog(stockLogList);
        }
        if (needAddedList != null && !needAddedList.isEmpty()) {
            List<String> newCodeList = needAddedList.stream().map(StockInfo::getCode)
                    .collect(Collectors.toList());
            stockInfoDao.setStockIdByCodeType(newCodeList, StockConsts.StockLogType.New.value());
        }
    }

    private void add(List<StockInfo> list) {
        Assert.notNull(list, StockServiceImpl.LIST_MESSAGE);
        if (!list.isEmpty()) {
            stockInfoDao.add(list);
        }
    }

    private void update(List<StockInfo> list) {
        Assert.notNull(list, StockServiceImpl.LIST_MESSAGE);
        if (!list.isEmpty()) {
            stockInfoDao.update(list);
        }
    }

    @Override
    public void saveDailyIndexToFile(String rootPath) {
        List<StockInfo> list = getAll().stream().filter(stockInfo ->
            !StockUtil.isCompositeIndex(stockInfo.getExchange(), stockInfo.getCode())
        ).collect(Collectors.toList());

        File root = new File(rootPath);

        list.forEach(stockInfo -> {
            logger.info("start save {}: {}", stockInfo.getName(), stockInfo.getCode());
            try {
                File file = new File(root, stockInfo.getExchange() + "/" + stockInfo.getCode() + ".txt");
                if (file.length() < 5 * 1024) {
                    String content = stockCrawlerService.getHistoryDailyIndexsString(stockInfo.getCode());
                    try (FileWriter out = new FileWriter(file)) {
                        FileCopyUtils.copy(content, out);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void saveDailyIndexFromFile(String rootPath) {
        List<StockInfo> list = getAll().stream().filter(stockInfo ->
            !StockUtil.isCompositeIndex(stockInfo.getExchange(), stockInfo.getCode())
        ).collect(Collectors.toList());

        File root = new File(rootPath);

        list.forEach(stockInfo -> {
            logger.info("start save {}: {}", stockInfo.getName(), stockInfo.getCode());
            try {
                File file = new File(root, stockInfo.getExchange() + "/" + stockInfo.getCode() + ".txt");
                try (FileReader in = new FileReader(file)) {
                    String content = FileCopyUtils.copyToString(in);
                    List<DailyIndex> dailyIndexList = dailyIndexParser.parseHistoryDailyIndexList(content);
                    dailyIndexList.forEach(dailyIndex -> dailyIndex.setStockInfoId(stockInfo.getId()));
                    dailyIndexDao.save(dailyIndexList);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void saveDailyIndex(DailyIndex dailyIndex) {
        dailyIndexDao.save(dailyIndex);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveDailyIndex(List<DailyIndex> list) {
        dailyIndexDao.save(list);
    }

    @Override
    public PageVo<StockInfo> getStockList(PageParam pageParam) {
        return stockInfoDao.get(pageParam);
    }

    @Override
    public boolean existsTodayDailyIndex() {
        return dailyIndexDao.getDailyIndexByFullCodeAndDate("sz000001", new Date()) != null;
    }

    @Cacheable(value = StockConsts.CACHE_KEY_DATA_CODE, key = "#code")
    @Override
    public StockInfo getStockByFullCode(String code) {
        return stockInfoDao.getStockByFullCode(code);
    }

    @Override
    public PageVo<DailyIndexVo> getDailyIndexList(PageParam pageParam) {
        pageParam.getCondition().put("date", DateUtils.formatDate(new Date(), "yyyy-MM-dd") );
        return dailyIndexDao.getDailyIndexList(pageParam);
    }

}
