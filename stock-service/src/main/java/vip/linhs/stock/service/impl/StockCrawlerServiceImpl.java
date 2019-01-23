package vip.linhs.stock.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.model.po.StockInfo;
import vip.linhs.stock.parser.DailyIndexParser;
import vip.linhs.stock.parser.StockInfoParser;
import vip.linhs.stock.service.StockCrawlerService;
import vip.linhs.stock.util.HttpUtil;
import vip.linhs.stock.util.StockConsts.StockState;
import vip.linhs.stock.util.StockUtil;

@Service
public class StockCrawlerServiceImpl implements StockCrawlerService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private StockInfoParser stockInfoParser;

    @Autowired
    private DailyIndexParser dailyIndexParser;

    @Override
    public List<StockInfo> getStockList() {
        String content = HttpUtil.sendGet(httpClient, "http://quote.eastmoney.com/stocklist.html", "gbk");
        if (content != null) {
            return stockInfoParser.parseStockInfoList(content);
        }
        return Collections.emptyList();
    }

    @Override
    public StockState getStockState(String code) {
        String content = HttpUtil.sendGet(httpClient,
                "http://quote.eastmoney.com/" + StockUtil.getFullCode(code) + ".html", "gb2312");
        if (content != null) {
            return stockInfoParser.parseStockState(content);
        }

        return null;
    }

    @Override
    public DailyIndex getDailyIndex(String code) {
        String content = HttpUtil.sendGet(httpClient, "http://hq.sinajs.cn/list=" + StockUtil.getFullCode(code), "gbk");
        if (content != null) {
            return dailyIndexParser.parseDailyIndex(content);
        }
        return null;
    }

    @Override
    public List<DailyIndex> getHistoryDailyIndexs(String code) {
        String content = HttpUtil.sendGet(httpClient, "http://www.aigaogao.com/tools/history.html?s=" + code, "gbk");
        if (content != null) {
            return dailyIndexParser.parseHistoryDailyIndexList(content);
        }
        return Collections.emptyList();
    }

    @Override
    public String getHistoryDailyIndexsString(String code) {
        return HttpUtil.sendGet(httpClient, "http://www.aigaogao.com/tools/history.html?s=" + code, "gbk");
    }

}
