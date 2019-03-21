package com.roxi.homework.level1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class Cache {
    @Resource(name = "map")
    private Map<String,Object> map=new HashMap<>();
    @Pointcut("execution(public * com.roxi.homework.level1.controller.*.*(..))")
    public void cache(){

    }

    @Around("cache()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
     //   System.out.println("go");
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
        Method method=methodSignature.getMethod();
        Annotation annotation= method.getAnnotation(com.roxi.homework.level1.annotation.Cache.class);
        System.out.println(annotation);
        if(annotation!=null){
            String key=method.getName()+ Arrays.toString(pjp.getArgs());
            Object value=map.get(key);
            if(value!=null){
                return value;
            }
        }
        return pjp.proceed();
    }

    @AfterReturning(returning = "object" ,pointcut = "cache()")
    public void after(JoinPoint joinPoint,Object object){
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
        Method method=methodSignature.getMethod();
        Annotation annotation=method.getAnnotation(com.roxi.homework.level1.annotation.Cache.class);
        if(annotation!=null){
            String key= method.getName()+Arrays.toString(joinPoint.getArgs());
            map.putIfAbsent(key, object);
        }
   //     System.out.println(object);
    //    System.out.println("end");
    }
}
