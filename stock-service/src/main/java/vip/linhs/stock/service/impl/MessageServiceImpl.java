package vip.linhs.stock.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.linhs.stock.model.po.Message;
import vip.linhs.stock.service.MessageService;
import vip.linhs.stock.util.HttpUtil;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private CloseableHttpClient httpClient;

    @Override
    public void sendDingding(Message message) {
        Map<String, Object> params = buildMessageParams(message.getBody());
        String content = HttpUtil.sendPostJson(httpClient, message.getTarget(), params);
        logger.info("发送消息返回结果: {}", content);
    }

    private Map<String, Object> buildMessageParams(String content) {
        HashMap<String, Object> text = new HashMap<>();
        text.put("content", content);

        HashMap<String, Object> params = new HashMap<>();
        params.put("msgtype", "text");
        params.put("text", text);

        return params;
    }

}
