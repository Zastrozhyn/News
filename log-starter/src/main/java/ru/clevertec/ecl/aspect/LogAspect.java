package ru.clevertec.ecl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(@ru.clevertec.ecl.annotation.Log * *(..))")
    private void annotatedMethods() {
    }

    @Pointcut("within(@ru.clevertec.ecl.annotation.Log *)")
    private void annotatedClass() {
    }

    @Around("annotatedMethods() || annotatedClass()")
    public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object methodResult = joinPoint.proceed(joinPoint.getArgs());
        String logMessage = new StringBuilder("Called method: ")
            .append(joinPoint.getTarget().getClass().getSimpleName())
            .append(" ")
            .append(joinPoint.getSignature().getName())
            .append("\n")
            .append(Arrays.toString(joinPoint.getArgs()))
            .append('\n')
            .append(methodResult)
            .toString();
        log.info(logMessage);
        return methodResult;
    }
}
