package com.ojBacked.anno;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class GuiguAspect {
    @Around("execution(* com.ojBacked.controller.*.*(..)) && @annotation(guigu))")
    public Object login(ProceedingJoinPoint proceedingJoinPoint, Guigu guigu) throws Throwable {
        //1 获取request对象
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
        HttpServletRequest request = sra.getRequest();

        //2 从请求头获取token
        String token = request.getHeader("token");
        //3 判断token是否为空，如果为空，返回登录提示
        if (!StringUtils.hasText(token)) {
            throw new RuntimeException();
        }
        System.out.println("token==>:" + token);
        //6 执行业务方法
        return proceedingJoinPoint.proceed();
    }

}
