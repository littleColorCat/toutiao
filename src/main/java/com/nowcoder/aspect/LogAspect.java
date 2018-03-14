package com.nowcoder.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2018/3/14.
 */
//注解：Aspect
@Aspect
@Component
public class LogAspect {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogAspect.class);


    //执行execution(* com.nowcoder.controller..IndexController的方法前调用该函数
    @Before("execution(* com.nowcoder.controller..IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(Object obj : joinPoint.getArgs()){
            sb.append("arg:" + obj + "|");
        }
        logger.info("before method" + sb.toString());
    }

    //执行execution(* com.nowcoder.controller..IndexController的方法后调用该函数
    @After("execution(* com.nowcoder.controller..IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        logger.info("after method");
    }
}
