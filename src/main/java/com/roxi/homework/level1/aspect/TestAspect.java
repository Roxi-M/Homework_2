package com.roxi.homework.level1.aspect;

import com.roxi.homework.level1.bean.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Component
@Aspect

public class TestAspect {
    @Pointcut("@annotation(com.roxi.homework.level1.annotation.Cut)")
    public void cut(){
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        if(user!=null) {
            if(!"admin".equals(user.getName())) return "403";
            return "已修改";
        }
        return "未登录，请先登陆";
    }
}
