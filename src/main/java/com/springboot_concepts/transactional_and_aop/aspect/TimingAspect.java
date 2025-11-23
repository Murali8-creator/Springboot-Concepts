package com.springboot_concepts.transactional_and_aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimingAspect {

    @Around("@annotation(com.springboot_concepts.transactional_and_aop.annotation.Loggable)")
    public Object measureTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("AOP TIMING: " + pjp.getSignature() + " executed in " + (end - start) + " ms");
        }
    }
}
