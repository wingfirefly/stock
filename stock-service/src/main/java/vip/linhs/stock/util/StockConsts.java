package vip.linhs.stock.util;

public class StockConsts {

    public static final String KEY_AUTH_USER_ID = "user_id";

    public static final String KEY_AUTH_TOKEN = "auth_token";

    enum Exchange {
        SH("sh"), SZ("sz");
        private String name;

        private Exchange(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
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
        Terminated(2),
        /**
         * 退市
         */
        Delisted(3);
        private int value;

        private StockState(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum StockLogType {
        New(0), Rename(1), Terminated(2), Delisted(3), ReListed(4);
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

    public enum TickerConfigKey {
        StockList("stock_list");
        private String value;

        private TickerConfigKey(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum MessageType {
        DingDing(0);
        private int value;

        private MessageType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

}
