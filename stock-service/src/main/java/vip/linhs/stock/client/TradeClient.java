package vip.linhs.stock.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.Consts;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import vip.linhs.stock.exception.UnauthorizedException;
import vip.linhs.stock.util.HttpUtil;

@Component
public class TradeClient {

    private ThreadLocal<ClientWrapper> threadLocal = new ThreadLocal<>();

    @Autowired
    private CloseableHttpClient httpClient;

    public String send(String url, Map<String, Object> params, Map<String, String> header) {
        String content = HttpUtil.sendPost(httpClient, url, params, header);
        if (content.contains("Object moved")) {
            throw new UnauthorizedException("unauthorized " + url);
        }
        return content;
    }

    public String sendNewInstance(String url, Map<String, Object> params) {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        ClientWrapper wrapper = new ClientWrapper();
        wrapper.httpClient = httpClient;
        wrapper.cookieStore  = cookieStore;
        threadLocal.set(wrapper);
        return HttpUtil.sendPost(httpClient, url, params);
    }

    public Map<String, String> getAuth() {
        HttpGet request = new HttpGet("https://jy.xzsec.com/Trade/Buy");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        ClientWrapper clientWrapper = threadLocal.get();
        threadLocal.remove();
        try (CloseableHttpClient client = clientWrapper.httpClient) {
            try (CloseableHttpResponse response = client.execute(request)) {
                String cookies = clientWrapper.cookieStore.getCookies().stream().map(cookie -> cookie.getName() + "=" + cookie.getValue()).collect(Collectors.joining("; "));

                String content = EntityUtils.toString(response.getEntity(), Consts.UTF_8.name());
                String key = "input id=\"em_validatekey\" type=\"hidden\" value=\"";
                int inputBegin = content.indexOf(key) + key.length();
                int inputEnd = content.indexOf("\" />", inputBegin);
                String validateKey = content.substring(inputBegin, inputEnd);

                HashMap<String, String> map = new HashMap<>();
                map.put("cookie", cookies);
                map.put("validateKey", validateKey);
                return map;
            }
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error on " + request.getMethod() + " request for \""
                    + request.getURI() + "\": " + ex.getMessage(), ex);
        }
    }

    private static class ClientWrapper {
        private CloseableHttpClient httpClient;
        private CookieStore cookieStore;
    }

}
