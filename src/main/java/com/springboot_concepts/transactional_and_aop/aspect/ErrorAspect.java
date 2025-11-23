package com.springboot_concepts.transactional_and_aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspect {

    @AfterThrowing(value = "execution(* com.springboot_concepts..*(..))", throwing = "ex")
    public void logError(JoinPoint jp, Exception ex) {
        System.out.println("AOP ERROR: " + jp.getSignature() + " -> " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
}
