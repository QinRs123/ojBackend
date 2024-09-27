package com.ojBacked.interceptor;


import com.ojBacked.comment.com.JwtUntil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 登录拦截器，校验jwt令牌
 */
@Slf4j
@Component  //交给spring容器管理
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        log.info("返回的token :{}",token);
        try {
            //校验token是否合法
            Map<String, Object> claims = JwtUntil.parseJWT(token);
            log.info(claims.toString());
            String id = (String) claims.get("id");
            log.info("id:{}",id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(401);
            response.getWriter().write("NOT_LOGIN");
            //令牌异常
            return false;
        }
    }

}
