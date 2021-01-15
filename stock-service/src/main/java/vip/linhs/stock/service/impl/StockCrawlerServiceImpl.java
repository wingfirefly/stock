package vip.linhs.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("eastmoneyStockInfoParser")
    private StockInfoParser stockInfoParser;

    @Autowired
    private DailyIndexParser dailyIndexParser;

    @Override
    public List<StockInfo> getStockList() {
        ArrayList<StockInfo> list = new ArrayList<>();
        list.addAll(getStockList("m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23,b:MK0021,b:MK0022,b:MK0023,b:MK0024"));
        return list;
    }

    private List<StockInfo> getStockList(String fs) {
        String content = HttpUtil.sendGet(httpClient, "http://20.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=10000000&np=1&fid=f3&fields=f12,f13,f14&fs=" + fs);
        if (content != null) {
            List<StockInfo> list = stockInfoParser.parseStockInfoList(content);
            list = list.stream().filter(v -> v.getExchange() != null).collect(Collectors.toList());
            list.forEach(stockInfo -> stockInfo.setAbbreviation(StockUtil.getPinyin(stockInfo.getName())));
            return list;
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
        String content = getHistoryDailyIndexsString(code);
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
