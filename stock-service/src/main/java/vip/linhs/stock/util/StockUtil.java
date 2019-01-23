package vip.linhs.stock.util;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.util.StringUtils;

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
        if (code.startsWith("600") || code.startsWith("601") || code.startsWith("603")) {
            return Exchange.SH.getName();
        }
        if (code.startsWith("000") || code.startsWith("002") || code.startsWith("003") || code.startsWith("004")
                || code.startsWith("300")) {
            return Exchange.SZ.getName();
        }
        return null;
    }

    public static BigDecimal calcIncreaseRate(BigDecimal a, BigDecimal b) {
        return DecimalUtil.div(DecimalUtil.sub(a, b), b);
    }

}
