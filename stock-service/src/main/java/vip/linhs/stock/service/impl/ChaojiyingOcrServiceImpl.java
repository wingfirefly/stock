package vip.linhs.stock.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.service.AbstractOcrService;
import vip.linhs.stock.util.HttpUtil;

@Service("chaojiyingOcrService")
public class ChaojiyingOcrServiceImpl extends AbstractOcrService {

    private final Logger logger = LoggerFactory.getLogger(ChaojiyingOcrServiceImpl.class);

    @Value("${ocr.third.chaojiying.user}")
    private String user;

    @Value("${ocr.third.chaojiying.pass}")
    private String pass;

    @Value("${ocr.third.chaojiying.softid}")
    private int softid;

    @Override
    protected String processBase64(String base64) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("pass", pass);
        params.put("softid", softid);
        params.put("codetype", 1004);
        params.put("file_base64", base64);
        String result = HttpUtil.sendPostJson(httpClient, "http://upload.chaojiying.net/Upload/Processing.php", params);
        logger.info("chaojiying result: {}", result);
        @SuppressWarnings("unchecked")
        Map<String, Object> map = JSON.parseObject(result, Map.class);
        return (String) map.get("pic_str");
    }

}
