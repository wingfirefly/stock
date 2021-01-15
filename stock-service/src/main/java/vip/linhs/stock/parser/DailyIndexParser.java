package vip.linhs.stock.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Component;

import vip.linhs.stock.model.po.DailyIndex;
import vip.linhs.stock.util.DecimalUtil;

@Component
public class DailyIndexParser {

    /*
     * 0：新晨科技, 股票名字; 1：27.55″, 今日开盘价; 2：27.25″, 昨日收盘价; 3：26.91″, 当前价格; 4：27.55″,
     * 今日最高价; 5：26.20″, 今日最低价; 6：26.91″, 竞买价, 即“买一报价; 7：26.92″, 竞卖价, 即“卖一报价;
     * 8：22114263″ 成交金额
     */
    public DailyIndex parseDailyIndex(String content) {
        String[] strs = content.split(",");
        if (strs.length <= 1) {
            return null;
        }
        BigDecimal openingPrice = new BigDecimal(strs[1]);
        BigDecimal preClosingPrice = new BigDecimal(strs[2]);
        BigDecimal closingPrice = new BigDecimal(strs[3]);
        BigDecimal highestPrice = new BigDecimal(strs[4]);
        BigDecimal lowestPrice = new BigDecimal(strs[5]);
        long tradingVolume = Long.parseLong(strs[8]);
        BigDecimal tradingValue = new BigDecimal(strs[9]);
        Date date = DateUtils.parseDate(strs[30], new String[] { "yyyy-MM-dd" });

        DailyIndex dailyIndex = new DailyIndex();
        dailyIndex.setOpeningPrice(openingPrice);
        dailyIndex.setPreClosingPrice(preClosingPrice);
        dailyIndex.setClosingPrice(closingPrice);
        dailyIndex.setHighestPrice(highestPrice);
        dailyIndex.setLowestPrice(lowestPrice);
        dailyIndex.setTradingVolume(tradingVolume);
        dailyIndex.setTradingValue(tradingValue);
        dailyIndex.setDate(date);

        return dailyIndex;
    }

    public List<DailyIndex> parseHistoryDailyIndexList(String content) {
        if (content.contains("Please check symbol.")) {
            return Collections.emptyList();
        }
        int tableStartIndex = content.indexOf("id=\"ctl16_contentdiv\"");
        if (tableStartIndex < 0) {
            return Collections.emptyList();
        }

        int trHeaderStartIndex = content.indexOf("<tr>", tableStartIndex);
        int trStartIndex = content.indexOf("<tr>", trHeaderStartIndex);
        int trEndIndex = content.indexOf("</tr>", trStartIndex);

        trStartIndex = content.indexOf("<tr>", trEndIndex);

        ArrayList<DailyIndex> list = new ArrayList<>(3000);
        while (trStartIndex > 0) {
            trEndIndex = content.indexOf("<tr>", trStartIndex + 2);
            String trContent = content.substring(trStartIndex, trEndIndex);
            if (trContent.contains("-- End --")) {
                break;
            }
            DailyIndex dailyIndex = DailyIndexParser.parseHistoryDailyIndex(trContent);
            list.add(dailyIndex);
            trStartIndex = content.indexOf("<tr>", trEndIndex);
        }

        Collections.sort(list, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        BigDecimal preClosingPrice = null;
        for (DailyIndex dailyIndex : list) {
            if (preClosingPrice == null) {
                preClosingPrice = dailyIndex.getOpeningPrice();
            }
            dailyIndex.setPreClosingPrice(preClosingPrice);
            preClosingPrice = dailyIndex.getClosingPrice();
        }
        return list;
    }

    private static DailyIndex parseHistoryDailyIndex(String content) {
        // 日期   开盘  最高  最低  收盘  成交量 成交金额
        // <tr><td><a name="06/06/2018">06/06/2018</a></td><td>23.10</td><td>25.45</td><td>22.72</td><td>24.28</td>
        // <td>17,867,500</td><td>433,192,000</td><td>0.68</td><td><span class='changeup'>2.88 %</span></td><td></td>
        // <td><span class='changeup'>12.02 %</span></td><td>10365.125</td><td><span class='changedn'>-0.20 %</class></td><
        content = content.replaceAll(" class=altertd", "");
        Pattern pattern = Pattern.compile("<td>([\\s\\S]{3,60}?)</td>");
        Matcher matcher = pattern.matcher(content);

        String[] values = new String[7];
        int index = 0;
        while (matcher.find()) {
            values[index++] = matcher.group(1);
            if (index == 7) {
                break;
            }
        }

        String dateStr = values[0].substring(9, 19);
        Date date = DateUtils.parseDate(dateStr, new String[] { "MM/dd/yyyy" });
        BigDecimal openingPrice = DecimalUtil.fromStr(values[1]);
        BigDecimal highestPrice = DecimalUtil.fromStr(values[2]);
        BigDecimal lowestPrice = DecimalUtil.fromStr(values[3]);
        BigDecimal closingPrice = DecimalUtil.fromStr(values[4]);
        long tradingVolume = Long.parseLong(values[5].replaceAll(",", ""));
        BigDecimal tradingValue = DecimalUtil.fromStr(values[6]);

        DailyIndex dailyIndex = new DailyIndex();
        dailyIndex.setOpeningPrice(openingPrice);
        dailyIndex.setClosingPrice(closingPrice);
        dailyIndex.setHighestPrice(highestPrice);
        dailyIndex.setLowestPrice(lowestPrice);
        dailyIndex.setTradingVolume(tradingVolume);
        dailyIndex.setTradingValue(tradingValue);
        dailyIndex.setDate(date);

        return dailyIndex;
    }

}
