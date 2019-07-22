package vip.linhs.stock.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import vip.linhs.stock.model.po.Message;
import vip.linhs.stock.service.MessageService;
import vip.linhs.stock.util.HttpUtil;
import vip.linhs.stock.util.StockConsts;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private CloseableHttpClient httpClient;

    @Override
    public void sendDingding(String body, String target) {
        Message message = new Message(StockConsts.MessageType.DingDing.value(), target, body, new Date());
        Map<String, Object> params = buildMessageParams(message.getBody());
        logger.info("发送消息内容: {}", JSON.toJSONString(message.getBody()));
        String result = HttpUtil.sendPostJson(httpClient, message.getTarget(), params);
        logger.info("发送消息返回结果: {}", result);
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
