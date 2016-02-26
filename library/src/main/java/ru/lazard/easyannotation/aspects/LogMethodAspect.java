package ru.lazard.easyannotation.aspects;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;


/**
 * Created by Egor on 25.02.2016.
 */
@Aspect
public class LogMethodAspect {


    @Around("execution(!synthetic * *(..)) && onLogMethod()")
    public Object doLogMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethod2(joinPoint);
    }

    @Pointcut("@within(ru.lazard.easyannotation.annotations.LogMethod)||@annotation(ru.lazard.easyannotation.annotations.LogMethod)")
    public void onLogMethod() {
    }

    private Object logMethod2(final ProceedingJoinPoint joinPoint) throws Throwable {
        Log.w(EasyAnnotation.TAG, "--> " + joinPoint.getSignature().toShortString() + " Args : " + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
        Object result = joinPoint.proceed();
        String type = ((MethodSignature) joinPoint.getSignature()).getReturnType().toString();
        Log.w(EasyAnnotation.TAG, "<-- " + joinPoint.getSignature().toShortString() + " Result : " + ("void".equalsIgnoreCase(type)?"void":result));
        return result;
    }
}


