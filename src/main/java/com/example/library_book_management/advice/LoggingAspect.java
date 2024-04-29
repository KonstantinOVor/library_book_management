package com.example.library_book_management.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Pointcut("within(com.example.library_book_management.controller.BookController)")
    public void withinController() {}
    @Around("withinController()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Выполнение метода: {} в классе: {}", methodName, className);

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("Выполнение метода: {} заняло {} мс", methodName, endTime - startTime);

        return proceed;
    }
}
