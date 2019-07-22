package vip.linhs.stock.api.response;

public class AuthenticationResponse {

    private String cookie;
    private String validateKey;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getValidateKey() {
        return validateKey;
    }

    public void setValidateKey(String validateKey) {
        this.validateKey = validateKey;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse [cookie=" + cookie + ", validateKey=" + validateKey + "]";
    }

}
