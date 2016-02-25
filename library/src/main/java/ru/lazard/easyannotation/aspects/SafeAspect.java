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
public class SafeAspect {



    @Around("onSafeMethod()")
    public Object doAroundSafeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result=null;
        try {
            result =joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            Log.w(EasyAnnotation.TAG, joinPoint.getSignature().toShortString()+ (joinPoint.getArgs()!=null?Arrays.deepToString(joinPoint.getArgs()):""));
            Log.w(EasyAnnotation.TAG, EasyAnnotation.getStringFromException(e));
        }
        return result;
    }


    @Pointcut("@within(ru.lazard.easyannotation.annotations.Safe)||@annotation(ru.lazard.easyannotation.annotations.Safe)")
    public void onSafeMethod() {
    }
}


