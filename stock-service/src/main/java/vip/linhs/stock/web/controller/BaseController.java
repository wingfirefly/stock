package vip.linhs.stock.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import vip.linhs.stock.util.StockConsts;

public abstract class BaseController {

    protected int getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            return -1;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        Integer userId = (Integer) request.getAttribute(StockConsts.KEY_AUTH_USER_ID);
        return userId != null ? userId : -1;
    }

}
