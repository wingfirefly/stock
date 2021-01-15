package vip.linhs.stock.util;

import java.util.NoSuchElementException;

public class StockConsts {

    public static final String KEY_AUTH_USER_ID = "user_id";

    public static final String KEY_AUTH_TOKEN = "auth-token";

    public static final String CACHE_KEY_PREFIX = "stock:";

    private static final String CACHE_KEY_DATA_PREFIX = CACHE_KEY_PREFIX + "data:";
    public static final String CACHE_KEY_DATA_STOCK = StockConsts.CACHE_KEY_DATA_PREFIX + "stock";
    public static final String CACHE_KEY_DATA_DAILYINDEX = StockConsts.CACHE_KEY_DATA_PREFIX + "dailyIndex";

    private static final String CACHE_KEY_CONFIG_PREFIX = CACHE_KEY_PREFIX + "config:";
    public static final String CACHE_KEY_CONFIG_TICKER = StockConsts.CACHE_KEY_CONFIG_PREFIX + "tickerConfig";
    public static final String CACHE_KEY_CONFIG_ROBOT = StockConsts.CACHE_KEY_CONFIG_PREFIX + "robot";

    private static final String CACHE_KEY_TRADE_PREFIX = CACHE_KEY_PREFIX + "trade:";
    public static final String CACHE_KEY_TRADE_USER = StockConsts.CACHE_KEY_TRADE_PREFIX + "tradeUser";
    public static final String CACHE_KEY_TRADE_RULE = StockConsts.CACHE_KEY_TRADE_PREFIX + "tradeRule";
    public static final String CACHE_KEY_TRADE_METHOD = StockConsts.CACHE_KEY_TRADE_PREFIX + "tradeMethod";

    public static final String CACHE_KEY_TOKEN = CACHE_KEY_PREFIX + "auth:token";

    public static final String CACHE_KEY_TRADE_STRATEGY = CACHE_KEY_PREFIX + "trade:tradeStrategy";

    public static final long DURATION_REDIS_DEFAULT = 3600 * 24 * 2;

    public enum Exchange {
        SH("sh"), SZ("sz");
        private String name;

        private Exchange(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isSh() {
            return name.equals("sh");
        }

        public boolean isSz() {
            return name.equals("sh");
        }

        public static Exchange valueOfName(String name) {
            for (Exchange exchange : Exchange.values()) {
                if (exchange.name.equals(name)) {
                    return exchange;
                }
            }
            throw new NoSuchElementException("no exchange named " + name);
        }

    }

    public enum StockState {
        /**
         * 上市
         */
        Listed(0),
        /**
         * 停牌
         */
        Suspended(1),
        /**
         * 终止上市
         */
        Terminated(2);
        private int value;

        private StockState(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum StockType {
        A(0), Index(1), ETF(2), B(3);
        private int value;

        private StockType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum StockLogType {
        New(0), Rename(1), Terminated(2);
        private int value;

        private StockLogType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum TaskState {
        Completed(0), InProgress(1), Pending(2);
        private int value;

        private TaskState(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum RobotType {
        DingDing(0), WetChat(1);
        private int value;

        private RobotType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum TradeState {
        Invalid(0), Valid(1);
        private int value;

        private TradeState(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum MessageType {
        DingDing(0), Email(1);
        private int value;

        private MessageType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

}
