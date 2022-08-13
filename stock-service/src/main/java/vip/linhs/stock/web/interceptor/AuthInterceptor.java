package vip.linhs.stock.web.interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import vip.linhs.stock.model.po.User;
import vip.linhs.stock.service.UserService;
import vip.linhs.stock.util.StockConsts;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private UserService userService;

    private final Set<String> openUrlList = new HashSet<>();

    public AuthInterceptor() {
        openUrlList.add("/user/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        int userId = getUserId(request);
        return handleAuth(request, response, userId);
    }

    private boolean handleAuth(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        String url = request.getRequestURI();
        logger.debug("request_api: {}", url);

        boolean isLogin = userId > 0;
        boolean isOpen = openUrlList.contains(url);

        if (!isOpen && !isLogin) {
            // 401
            writeJson(response, HttpStatus.UNAUTHORIZED.value(), "{\"message\": \"please login first\"}");
            return false;
        }
        request.setAttribute(StockConsts.KEY_AUTH_USER_ID, userId);
        return true;
    }

    private int getUserId(HttpServletRequest request) {
        String authToken = request.getHeader(StockConsts.KEY_AUTH_TOKEN);
        User user = userService.getByToken(authToken);
        if (user != null) {
            return user.getId();
        }
        return 0;
    }

    private void writeJson(HttpServletResponse response, int status, String data) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(data);
    }

}
