package ru.lazard.easyannotation.aspects;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;

import ru.lazard.easyannotation.annotations.Sync;


/**
 * Created by Egor on 25.02.2016.
 */
@Aspect
public class ThreadsAspect {


    @Around("execution(!synthetic * *(..)) && onAsyncMethod()&&onSafeMethod()")
    public Object doAroundAsyncSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return asyncMethod(joinPoint, true);
    }

    @Around("execution(!synthetic * *(..)) && onAsyncMethod()&&!onSafeMethod()")
    public Object doAroundAsyncUnSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return asyncMethod(joinPoint, false);
    }

    @Around("execution(!synthetic * *(..)) && onSafeMethod()&&!(onSyncMethod()||onAsyncMethod())")
    public Object doAroundSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return safeMethod(joinPoint);
    }

    @Around("execution(!synthetic * *(..)) && onSyncMethod()&&onSafeMethod()")
    public Object doAroundSyncSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return syncMethod(joinPoint, true);
    }

    @Around("execution(!synthetic * *(..)) && onSyncMethod()&&!onSafeMethod()")
    public Object doAroundSyncUnSafeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return syncMethod(joinPoint, false);
    }

    @Pointcut("@within(ru.lazard.easyannotation.annotations.Async)||@annotation(ru.lazard.easyannotation.annotations.Async)")
    public void onAsyncMethod() {
    }

    @Pointcut("@within(ru.lazard.easyannotation.annotations.Safe)||@annotation(ru.lazard.easyannotation.annotations.Safe)")
    public void onSafeMethod() {
    }

    @Pointcut("@within(ru.lazard.easyannotation.annotations.Sync)||@annotation(ru.lazard.easyannotation.annotations.Sync)")
    public void onSyncMethod() {
    }

    private Object asyncMethod(final ProceedingJoinPoint joinPoint, boolean isSafe) throws Throwable {
        Object result = null;
        Runnable runnable = createRunnable(joinPoint, isSafe);
        new Thread(runnable).start();
        return result;
    }

    private static Runnable createRunnable(final ProceedingJoinPoint joinPoint, final boolean isSafe) {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    execute(joinPoint, isSafe);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            }
        };
        return runnable;
    }

    private static Object execute(ProceedingJoinPoint joinPoint, boolean isSafe) throws Throwable {
        if (isSafe) {
            return executeSafe(joinPoint);
        } else {
            return executeUnsafe(joinPoint);
        }
    }

    private static Object executeSafe(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            Log.w(EasyAnnotation.TAG, joinPoint.getSignature().toShortString() + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
            Log.w(EasyAnnotation.TAG, getStringFromException(e));
        }
        return result;
    }

    private static Object executeUnsafe(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed(joinPoint.getArgs());
    }

    private static String getStringFromException(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    private Object safeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return execute(joinPoint, true);
    }

    private Object syncMethod(final ProceedingJoinPoint joinPoint, boolean isSafe) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Sync syncAnnotation = method.getAnnotation(Sync.class);
        long delay = syncAnnotation.delay();

        if (Looper.getMainLooper() == Looper.myLooper() && delay <= 0) {
            if (isSafe) {
                return execute(joinPoint, isSafe);
            }
        }

        Object result = null;

        Runnable runnable = createRunnable(joinPoint, isSafe);
        Handler handler = new Handler(Looper.getMainLooper());
        if (delay <= 0) {
            handler.post(runnable);
        } else {
            handler.postDelayed(runnable, delay);
        }

        return result;
    }
}


