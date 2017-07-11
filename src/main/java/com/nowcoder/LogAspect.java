package com.nowcoder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by Shirley on 2017/7/11.
 */
@Aspect//面向切面 用log举例
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.nowcoder.controller.*(..))")
    public void  beforeMethod(JoinPoint joinPoint){
        logger.info("before method:");

    }
    @After("execution(* com.nowcoder.controller.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        logger.info("after method:");
    }

}
