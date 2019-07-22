package vip.linhs.stock.model.po;

public class TradeUser extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String name;
    private String accountId;
    private String validateKey;
    private String cookie;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getValidateKey() {
        return validateKey;
    }

    public void setValidateKey(String validateKey) {
        this.validateKey = validateKey;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public String toString() {
        return "TradeUser [name=" + name + ", accountId=" + accountId + ", validateKey="
                + validateKey + ", cookie=" + cookie + ", toString()=" + super.toString() + "]";
    }

}
