package ru.lazard.easyannotation.aspects;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Created by Egor on 25.02.2016.
 */
public class EasyAnnotation {
    static final String TAG = "easyannotation";

    static Runnable createRunnable(final ProceedingJoinPoint joinPoint) {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    execute(joinPoint);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            }
        };
        return runnable;
    }

    static Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed(joinPoint.getArgs());
    }

    static Object executeSafe(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            Log.w(EasyAnnotation.TAG, joinPoint.getSignature().toShortString() + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
            Log.w(EasyAnnotation.TAG, EasyAnnotation.getStringFromException(e));
        }
        return result;
    }

    static String getStringFromException(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
