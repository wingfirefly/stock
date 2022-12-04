package vip.linhs.stock;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSON;

import vip.linhs.stock.util.HttpUtil;

//@SpringBootTest
public class StockApplicationTest {

    @Test
    public void test() throws InterruptedException, IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(2)
                .setConnectTimeout(2)
                .setSocketTimeout(2).build();

        FileWriter fw = new FileWriter("/data/map.java");

        Map<String, String> header = new HashMap<>();
        for (int i=10; i<10000; i++) {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            String icode = String.format("%04d", i);
            String url = "http://127.0.0.1:18888/api/verifyUserInfo?" + icode;
            String content = HttpUtil.sendGet(httpClient, url, header);
            System.out.println(url + " " + content);
            @SuppressWarnings("unchecked")
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String userInfo = map.get("userInfo");
            System.out.println(icode + ":" + userInfo);

            fw.write(icode + ":" + userInfo + "\r\n");

            httpClient.close();
        }

        fw.close();

    }

}
