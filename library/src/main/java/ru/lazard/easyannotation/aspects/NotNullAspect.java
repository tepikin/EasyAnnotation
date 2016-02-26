package ru.lazard.easyannotation.aspects;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;


/**
 * Created by Egor on 25.02.2016.
 */
@Aspect
public class NotNullAspect {


    @Around("execution(!synthetic * *(..)) && onNotNullMethod()")
    public Object doAroundNotNullMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return notNullMethod(joinPoint);
    }

    @Pointcut("@within(ru.lazard.easyannotation.annotations.NotNull)||@annotation(ru.lazard.easyannotation.annotations.NotNull)")
    public void onNotNullMethod() {
    }

    private Object notNullMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getArgs() == null) return joinPoint.proceed();
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) {
                Log.w(EasyAnnotation.TAG, " Null parameter exists : " + joinPoint.getSignature().toShortString() + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
                return null;
            }
        }
        return joinPoint.proceed();
    }
}


