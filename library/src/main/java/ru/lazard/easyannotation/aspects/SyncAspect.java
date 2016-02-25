package ru.lazard.easyannotation.aspects;

import android.os.Handler;
import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import ru.lazard.easyannotation.annotations.Sync;


/**
 * Created by Egor on 25.02.2016.
 */
@Aspect
public class SyncAspect {


    @Around("onMethod()")
    public Object doAroundMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Sync syncAnnotation = method.getAnnotation(Sync.class);
        long delay = syncAnnotation.delay();

        if (Looper.getMainLooper() == Looper.myLooper() && delay <= 0) {
            return EasyAnnotation.execute(joinPoint);
        }

        Object result = null;

        Runnable runnable = EasyAnnotation.createRunnable(joinPoint);
        Handler handler = new Handler(Looper.getMainLooper());
        if (delay <= 0) {
            handler.post(runnable);
        } else {
            handler.postDelayed(runnable, delay);
        }

        return result;
    }


    @Pointcut("@within(ru.lazard.easyannotation.annotations.Sync)||@annotation(ru.lazard.easyannotation.annotations.Sync)")
    public void onMethod() {
    }
}


