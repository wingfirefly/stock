package vip.linhs.stock.web.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import vip.linhs.stock.model.vo.PageParam;
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

    protected <T> List<T> subList(List<T> list, PageParam pageParam) {
        if (list.isEmpty()) {
            return list;
        }
        int start = pageParam.getStart();
        if (start > list.size()) {
            return Collections.emptyList();
        }
        int end = pageParam.getStart() + pageParam.getLength();
        if (end > list.size()) {
            end = list.size();
        }
        return list.subList(start, end);
    }

}
