package vip.linhs.stock.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import vip.linhs.stock.util.StockConsts;

public class AuthInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        int userId = 1;
        request.setAttribute(StockConsts.KEY_AUTH_USER_ID, userId);

        return handleAuth(request, response, userId);
    }

    private boolean handleAuth(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
         String url = request.getRequestURI();
         logger.info("request_api: {}", url);

        boolean isLogin = userId > 0;

        if (!isLogin) {
            // 401
            writeJson(response, HttpStatus.UNAUTHORIZED.value(), "{\"message\": \"请重新登录\"}");
            return false;
        }

        return true;
    }

    private void writeJson(HttpServletResponse response, int status, String data) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(data);
    }

}
