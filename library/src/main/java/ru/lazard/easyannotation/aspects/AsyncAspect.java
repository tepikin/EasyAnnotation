package ru.lazard.easyannotation.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Created by Egor on 25.02.2016.
 */
@Aspect
public class AsyncAspect {



    @Around("onMethod()")
    public Object doAroundMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result=null;

        Runnable runnable = EasyAnnotation.createRunnable(joinPoint);

        new Thread(runnable).start();

        return result;
    }



    @Pointcut("@within(ru.lazard.easyannotation.annotations.Async)||@annotation(ru.lazard.easyannotation.annotations.Async)")
    public void onMethod() {
    }
}


