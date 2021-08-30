package vip.linhs.stock.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import vip.linhs.stock.api.TradeResultVo;
import vip.linhs.stock.api.request.AuthenticationRequest;
import vip.linhs.stock.api.request.BaseTradeListRequest;
import vip.linhs.stock.api.request.BaseTradeRequest;
import vip.linhs.stock.api.request.GetAssetsRequest;
import vip.linhs.stock.api.request.SubmitRequest;
import vip.linhs.stock.api.response.AuthenticationResponse;
import vip.linhs.stock.client.TradeClient;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.service.AbstractTradeApiService;
import vip.linhs.stock.service.SystemConfigService;
import vip.linhs.stock.service.TradeService;

@Service
public class TradeApiServiceImpl extends AbstractTradeApiService {

    private final Logger logger = LoggerFactory.getLogger(TradeApiServiceImpl.class);

    private static final List<String> IgnoreList = Arrays.asList("class", "userId", "method");

    private final ResponseParser revokeResponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            TradeResultVo<T> resultVo = new TradeResultVo<>();
            resultVo.setData(Collections.emptyList());
            resultVo.setStatus(TradeResultVo.STATUS_SUCCESS);
            resultVo.setMessage(content);
            return resultVo;
        }

        @Override
        public String name() {
            return "revokeResponseParser";
        }
    };

    private final ResponseParser v3ReponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            T t = JSON.parseObject(content, responseType);
            ArrayList<T> newList = new ArrayList<>();
            newList.add(t);

            TradeResultVo<T> resultVo = new TradeResultVo<>();
            resultVo.setData(newList);

            return resultVo;
        }

        @Override
        public String name() {
            return "v3ReponseParser";
        }
    };

    private final ResponseParser defaultReponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            TradeResultVo<T> resultVo = JSON.parseObject(content, new TypeReference<TradeResultVo<T>>() {});
            if (resultVo.isSuccess()) {
                List<T> list = resultVo.getData();
                ArrayList<T> newList = new ArrayList<>();
                if (list != null) {
                    list.forEach(d -> {
                        String text = JSON.toJSONString(d);
                        T t = JSON.parseObject(text, responseType);
                        newList.add(t);
                    });
                }
                resultVo.setData(newList);
            } else {
                resultVo.setData(Collections.emptyList());
            }
            return resultVo;
        }

        @Override
        public String name() {
            return "defaultReponseParser";
        }
    };

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeClient tradeClient;

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public <T> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType) {
        return send(request, responseType, false);
    }

    @Override
    public <T> TradeResultVo<T> sendList(BaseTradeListRequest request, TypeReference<T> responseType) {
        return send(request, responseType, true);
    }

    private String getMockData(BaseTradeRequest request) {
        if (request instanceof SubmitRequest) {
            return "{\"Message\":null,\"Status\":0,\"Data\":[{\"Wtbh\": \"" + System.currentTimeMillis() + "\"}]}";
        } else if (request instanceof GetAssetsRequest) {
            return "{\"Message\":null,\"Status\":0,\"Data\":[{\"Zzc\": \"100000\", \"Kyzj\": \"50000\", \"Kqzj\": \"80000\", \"Djzj\": \"20000\" }]}";
        }
        return "{\"Message\":null,\"Status\":0,\"Data\":[]}";
    }

    private <T> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType, boolean isSendList) {
        ResponseParser responseParse = getResponseParser(request);

        String url = getUrl(request);
        Map<String, String> header = getHeader(request);

        List<Map<String, Object>> paramList = null;
        Map<String, Object> params = null;
        if (isSendList) {
            paramList = ((BaseTradeListRequest) request).getList().stream().map(this::getParams).collect(Collectors.toList());
            logger.debug("trade {} request: {}", request.getMethod(), paramList);
        } else {
            params = getParams(request);
            logger.debug("trade {} request: {}", request.getMethod(), params);
        }

        String content;
        if (systemConfigService.isMock()) {
            content = getMockData(request);
        } else {
            if (isSendList) {
                content = tradeClient.sendListJson(url, paramList, header);
            } else {
                content = tradeClient.send(url, params, header);
            }
        }
        logger.debug("trade {} response: {}", request.getMethod(), content);

        return responseParse.parse(content, responseType);
    }

    @Override
    public TradeResultVo<AuthenticationResponse> authentication(AuthenticationRequest request) {
        TradeMethod tradeMethod = tradeService.getTradeMethodByName(request.getMethod());
        TradeUser tradeUser = tradeService.getTradeUserById(request.getUserId());

        Map<String, Object> params = getParams(request);
        params.put("userId", tradeUser.getAccountId());
        try {
            tradeClient.openSession();
            String content = tradeClient.sendNewInstance(tradeMethod.getUrl(), params);
            ResponseParser responseParse = defaultReponseParser;
            TradeResultVo<AuthenticationResponse> resultVo = responseParse.parse(content, new TypeReference<AuthenticationResponse>() {});
            if (resultVo.isSuccess()) {
                TradeMethod authCheckTradeMethod = tradeService.getTradeMethodByName(BaseTradeRequest.TradeRequestMethod.AuthenticationCheckRequest.value());

                String content2 = tradeClient.sendNewInstance(authCheckTradeMethod.getUrl(), new HashMap<>());
                String validateKey = getValidateKey(content2);

                AuthenticationResponse response = new AuthenticationResponse();
                response.setCookie(tradeClient.getCurrentCookie());
                response.setValidateKey(validateKey);
                resultVo.setData(Arrays.asList(response));
            }
            return resultVo;
        } finally {
            tradeClient.destoryCurrentSession();
        }
    }

    private String getValidateKey(String content) {
        String key = "input id=\"em_validatekey\" type=\"hidden\" value=\"";
        int inputBegin = content.indexOf(key) + key.length();
        int inputEnd = content.indexOf("\" />", inputBegin);
        String validateKey = content.substring(inputBegin, inputEnd);
        return validateKey;
    }

    private ResponseParser getResponseParser(BaseTradeRequest request) {
        if (BaseTradeRequest.TradeRequestMethod.RevokeRequest.value().equals(request.getMethod())) {
            return revokeResponseParser;
        }
        if (BaseTradeRequest.TradeRequestMethod.GetCanBuyNewStockListV3.value().equals(request.getMethod())) {
            return v3ReponseParser;
        }
        return defaultReponseParser ;
    }

    private Map<String, Object> getParams(Object request) {
        Map<Object, Object> beanMap = new BeanMap(request);
        HashMap<String, Object> params = new HashMap<>();
        beanMap.entrySet().stream().filter(entry -> !TradeApiServiceImpl.IgnoreList.contains(entry.getKey()))
                .forEach(entry -> params.put(String.valueOf(entry.getKey()), entry.getValue()));
        return params;
    }

    private String getUrl(BaseTradeRequest request) {
        TradeMethod tradeMethod = tradeService.getTradeMethodByName(request.getMethod());
        TradeUser tradeUser = tradeService.getTradeUserById(request.getUserId());
        String url = tradeMethod.getUrl();
        return url.replace("${validatekey}", tradeUser.getValidateKey()) ;
    }

    private Map<String, String> getHeader(BaseTradeRequest request) {
        TradeUser tradeUser = tradeService.getTradeUserById(request.getUserId());

        HashMap<String, String> header = new HashMap<>();
        header.put("cookie", tradeUser.getCookie());
        return header;
    }

    private static interface ResponseParser {
        <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType);
        String name();
    }

}
