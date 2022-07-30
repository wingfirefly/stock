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
import vip.linhs.stock.api.response.AuthenticationResponse;
import vip.linhs.stock.api.response.BaseTradeResponse;
import vip.linhs.stock.client.TradeClient;
import vip.linhs.stock.model.po.TradeMethod;
import vip.linhs.stock.model.po.TradeUser;
import vip.linhs.stock.service.AbstractTradeApiService;
import vip.linhs.stock.service.TradeService;
import vip.linhs.stock.util.RSAUtil;

@Service
public class TradeApiServiceImpl extends AbstractTradeApiService {

    private final Logger logger = LoggerFactory.getLogger(TradeApiServiceImpl.class);

    private static final List<String> IgnoreList = Arrays.asList("class", "userId", "method");

    public static class TradeResult<T> {
        private String Message;
        private int Status;
        private T Data;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public T getData() {
            return Data;
        }

        public void setData(T data) {
            Data = data;
        }
    }

    private final ResponseParser dataObjReponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            TradeResultVo<T> resultVo = new TradeResultVo<>();
            ArrayList<T> newList = new ArrayList<>();

            TradeResult<T> result = JSON.parseObject(content, new TypeReference<TradeResult<T>>() {});
            if (TradeResultVo.success(result.getStatus())) {
                String text = JSON.toJSONString(result.Data);
                T t = JSON.parseObject(text, responseType);
                newList.add(t);
            } else {
                resultVo.setData(Collections.emptyList());
            }

            resultVo.setMessage(result.getMessage());
            resultVo.setStatus(result.getStatus());
            resultVo.setData(newList);
            return resultVo;
        }

        @Override
        public int version() {
            return BaseTradeRequest.VERSION_DATA_OBJ;
        }

    };

    private final ResponseParser msgResponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            TradeResultVo<T> resultVo = new TradeResultVo<>();
            resultVo.setData(Collections.emptyList());
            resultVo.setStatus(TradeResultVo.STATUS_SUCCESS);
            resultVo.setMessage(content);
            return resultVo;
        }

        @Override
        public int version() {
            return BaseTradeRequest.VERSION_MSG;
        }
    };

    private final ResponseParser objReponseParser = new ResponseParser() {
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
        public int version() {
            return BaseTradeRequest.VERSION_OBJ;
        }
    };

    private final ResponseParser dataListReponseParser = new ResponseParser() {
        @Override
        public <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType) {
            TradeResultVo<T> resultVo = JSON.parseObject(content, new TypeReference<TradeResultVo<T>>() {});
            if (resultVo.success()) {
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
        public int version() {
            return BaseTradeRequest.VERSION_DATA_LIST;
        }
    };

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeClient tradeClient;

    @Override
    public <T extends BaseTradeResponse> TradeResultVo<T> send(BaseTradeRequest request, TypeReference<T> responseType) {
        ResponseParser responseParse = getResponseParser(request);

        String url = getUrl(request);
        logger.debug("trade {} url: {}", request.getMethod(), url);
        Map<String, String> header = getHeader(request);

        List<Map<String, Object>> paramList = null;
        Map<String, Object> params = null;

        boolean isSendList = request instanceof BaseTradeListRequest;
        if (isSendList) {
            paramList = ((BaseTradeListRequest) request).getList().stream().map(this::getParams).collect(Collectors.toList());
            logger.debug("trade {} request: {}", request.getMethod(), paramList);
        } else {
            params = getParams(request);
            logger.debug("trade {} request: {}", request.getMethod(), params);
        }

        String content;
        if (isSendList) {
            content = tradeClient.sendListJson(url, paramList, header);
        } else {
            content = tradeClient.send(url, params, header);
        }
        logger.debug("trade {} response: {}", request.getMethod(), content);
        return responseParse.parse(content, responseType);
    }

    @Override
    public TradeResultVo<AuthenticationResponse> authentication(AuthenticationRequest request) {
        TradeMethod tradeMethod = tradeService.getTradeMethodByName(request.getMethod());
        TradeUser tradeUser = tradeService.getTradeUserById(request.getUserId());

        request.setPassword(encodePassword(request.getPassword()));

        Map<String, String> header = getHeader(request);
        Map<String, Object> params = getParams(request);
        params.put("userId", tradeUser.getAccountId());
        try {
            tradeClient.openSession();
            String content = tradeClient.sendNewInstance(tradeMethod.getUrl(), params, header);
            ResponseParser responseParse = dataListReponseParser;
            TradeResultVo<AuthenticationResponse> resultVo = responseParse.parse(content, new TypeReference<AuthenticationResponse>() {});
            if (resultVo.success()) {
                TradeMethod authCheckTradeMethod = tradeService.getTradeMethodByName(BaseTradeRequest.TradeRequestMethod.AuthenticationCheck.value());
                AuthenticationResponse response = new AuthenticationResponse();
                String cookie = tradeClient.getCurrentCookie();
                response.setCookie(cookie);

                String content2 = tradeClient.sendNewInstance(authCheckTradeMethod.getUrl(), new HashMap<>(), header);
                String validateKey = getValidateKey(content2);

                response.setValidateKey(validateKey);
                resultVo.setData(Arrays.asList(response));
            }
            return resultVo;
        } finally {
            tradeClient.destoryCurrentSession();
        }
    }

    private String encodePassword(String password) {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHdsyxT66pDG4p73yope7jxA92\nc0AT4qIJ/xtbBcHkFPK77upnsfDTJiVEuQDH+MiMeb+XhCLNKZGp0yaUU6GlxZdp\n+nLW8b7Kmijr3iepaDhcbVTsYBWchaWUXauj9Lrhz58/6AE/NF0aMolxIGpsi+ST\n2hSHPu3GSXMdhPCkWQIDAQAB";
        return RSAUtil.encodeWithPublicKey(password, publicKey);
    }

    private String getValidateKey(String content) {
        String key = "input id=\"em_validatekey\" type=\"hidden\" value=\"";
        int inputBegin = content.indexOf(key) + key.length();
        int inputEnd = content.indexOf("\" />", inputBegin);
        String validateKey = content.substring(inputBegin, inputEnd);
        return validateKey;
    }

    private ResponseParser getResponseParser(BaseTradeRequest request) {
        if (request.responseVersion() == dataObjReponseParser.version()) {
            return dataObjReponseParser;
        }
        if (request.responseVersion() == msgResponseParser.version()) {
            return msgResponseParser;
        }
        if (request.responseVersion() == objReponseParser.version()) {
            return objReponseParser;
        }
        return dataListReponseParser ;
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
        if (!(request instanceof AuthenticationRequest)) {
            header.put("cookie", tradeUser.getCookie());
        }
        header.put("gw_reqtimestamp", System.currentTimeMillis() + "");
        header.put("X-Requested-With", "XMLHttpRequest");
        return header;
    }

    private static interface ResponseParser {
        <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType);
        int version();
    }

}
