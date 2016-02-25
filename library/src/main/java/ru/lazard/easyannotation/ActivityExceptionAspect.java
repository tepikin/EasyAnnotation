package ru.lazard.easyannotation;

import android.app.Activity;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Created by Egor on 25.02.2016.
 */
@Aspect
public class ActivityExceptionAspect {

    private static final String TAG = "easyannotation";

    @Around("onExceptionMethod()")
    public Object doAroundExceptionMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result=null;
        Log.i(TAG, "aspectJ " + joinPoint.getSignature().toShortString());
        try {
            result =joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Activity activity = (Activity) joinPoint.getThis();
        Log.i(TAG, "aspectJ " + activity);
        return result;
    }

    @Pointcut("execution(* android.app.Activity.onCreate(..))" +
            "||execution(* android.app.Activity.onDestroy(..))" +
            "||execution(* android.app.Activity.onStart(..))" +
            "||execution(* android.app.Activity.onStop(..))" +
            "||execution(* android.app.Activity.onResume(..))" +
            "||execution(* android.app.Activity.onPause(..))" +
            "||execution(* android.view.View.OnClickListener.onClick(..))" +
            "||execution(* android.view.View.OnTouchListener.onTouch(..))")
    public void onExceptionMethod() {
    }

//    @Pointcut("@within(hugo.weaving.DebugLog)||@annotation(hugo.weaving.DebugLog)")
//    public void onExceptionMethod() {
//    }
}


