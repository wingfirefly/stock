package vip.linhs.stock.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.ResourceAccessException;

import com.alibaba.fastjson.JSON;

public class HttpUtil {

    private HttpUtil() {
    }

    public static String sendGet(CloseableHttpClient httpClient, String url) {
        return HttpUtil.sendGet(httpClient, url, null, null);
    }

    public static String sendGet(CloseableHttpClient httpClient, String url, Map<String, String> header) {
        return HttpUtil.sendGet(httpClient, url, header, null);
    }

    public static String sendGet(CloseableHttpClient httpClient, String url, String charset) {
        return HttpUtil.sendGet(httpClient, url, null, charset);
    }

    public static String sendGet(CloseableHttpClient httpClient, String url, Map<String, String> header, String charset) {
        HttpGet httpGet = HttpUtil.getHttpGet(url);
        if (header != null) {
            header.entrySet().stream().forEach(entry -> httpGet.addHeader(entry.getKey(), entry.getValue()));
        }
        if (charset == null) {
            charset = Consts.UTF_8.name();
        }
        return HttpUtil.sendRequest(httpClient, httpGet, charset);
    }

    public static String sendPost(CloseableHttpClient httpClient, String url, List<BasicNameValuePair> parameters) {
        return HttpUtil.sendPost(httpClient, url, parameters, null);
    }

    public static String sendPost(CloseableHttpClient httpClient, String url, List<BasicNameValuePair> parameters, Map<String, String> header) {
        HttpPost httpPost = HttpUtil.getHttpPost(url);

        if (header != null) {
            header.entrySet().stream().forEach(entry -> httpPost.addHeader(entry.getKey(), entry.getValue()));
        }

        if (parameters != null) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, Consts.UTF_8);
            httpPost.setEntity(entity);
        }

        return HttpUtil.sendRequest(httpClient, httpPost, Consts.UTF_8.name());
    }

    public static String sendPostJson(CloseableHttpClient httpClient, String url, Map<String, Object> params) {
        HttpPost httpPost = HttpUtil.getHttpPost(url);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");

        String json = JSON.toJSONString(params);
        StringEntity entity = new StringEntity(json, Consts.UTF_8);
        httpPost.setEntity(entity);

        return HttpUtil.sendRequest(httpClient, httpPost, Consts.UTF_8.name());
    }

    private static String sendRequest(CloseableHttpClient httpClient, HttpUriRequest request, String charset) {
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error on " + request.getMethod() + " request for \""
                    + request.getURI() + "\": " + ex.getMessage(), ex);
        }
    }

    private static HttpGet getHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3500).setConnectTimeout(3500).build();
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    private static HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3500).setConnectTimeout(3500).build();
        httpPost.setConfig(requestConfig);
        return httpPost;
    }

}
