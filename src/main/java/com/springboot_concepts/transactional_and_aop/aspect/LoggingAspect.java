package com.springboot_concepts.transactional_and_aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.springboot_concepts..*(..))")
    public void logEnter(JoinPoint jp) {
        System.out.println("AOP ENTER: " + jp.getSignature() + " args=" + Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(value = "execution(* com.springboot_concepts..*(..))", returning = "result")
    public void logExit(JoinPoint jp, Object result) {
        System.out.println("AOP EXIT: " + jp.getSignature() + " returned=" + result);
    }
}
