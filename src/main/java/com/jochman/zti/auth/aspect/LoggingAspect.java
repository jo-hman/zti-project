package com.jochman.zti.auth.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method execution time.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs the execution time of methods in the AuthenticationService.
     * @param joinPoint the join point at which the advice is applied
     * @return the result of the method execution
     * @throws Throwable if the method throws an exception
     */
    @Around("execution(* com.jochman.zti.auth.service.AuthenticationService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        LOG.info("Method {} executed in {} ms with arguments: {}", joinPoint.getSignature(), executionTime, joinPoint.getArgs());

        return result;
    }
}
