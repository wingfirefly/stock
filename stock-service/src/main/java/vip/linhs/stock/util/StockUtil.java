package vip.linhs.stock.util;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.util.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import vip.linhs.stock.util.StockConsts.Exchange;

public class StockUtil {

    private StockUtil() {
    }

    public static String getFullCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        String exchange = StockUtil.getExchange(code);
        if (exchange == null) {
            return code;
        }
        return exchange + code;
    }

    public static boolean isCompositeIndex(String exchange, String code) {
        return Arrays.asList("sh000001").contains(exchange + code);
    }

    public static boolean isStockCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        return StockUtil.getExchange(code) != null;
    }

    public static String getExchange(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        if (code.startsWith("600") || code.startsWith("601") || code.startsWith("603") || code.startsWith("688")) {
            return Exchange.SH.getName();
        }
        if (code.startsWith("000") || code.startsWith("001") | code.startsWith("002") || code.startsWith("300")) {
            return Exchange.SZ.getName();
        }
        return null;
    }

    public static String getPinyin(String name) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder sb = new StringBuilder();
        for (char ch : name.toLowerCase().toCharArray()) {
            if (ch == '*') {
                continue;
            }
            if (ch >= 'a' && ch <= 'z') {
                sb.append(ch);
            } else if (ch == '行') {
                sb.append('h');
            } else {
                try {
                    String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);
                    if (arr == null) {
                        throw new RuntimeException("not support character " + name);
                    }
                    sb.append(arr[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    throw new RuntimeException("get pinyin error", e);
                }
            }
        }
        return sb.toString();
    }

    public static BigDecimal calcIncreaseRate(BigDecimal a, BigDecimal b) {
        return DecimalUtil.div(DecimalUtil.sub(a, b), b);
    }

}
