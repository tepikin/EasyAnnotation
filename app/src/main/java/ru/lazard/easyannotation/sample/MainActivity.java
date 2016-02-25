package ru.lazard.easyannotation.sample;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ru.lazard.easyannotation.annotations.Safe;
import ru.lazard.easyannotation.annotations.Sync;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static String TAG ="easyannotation";//MainActivity.class.getSimpleName();

    @Sync(delay = 1000)
    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick " + v+" thread="+Thread.currentThread().getId());
       // throw new RuntimeException("test");
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.e(TAG, "onTouch v="+v+" event="+event);
        return false;
    }
    
    @Safe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate thread=" +Thread.currentThread().getId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate");
        View viewById = findViewById(R.id.button);
        viewById.setOnClickListener(this);
        viewById.setOnTouchListener(this);
        
        new Thread(new Runnable(){
            @UiThread
            @Override
            public void run() {
                
                Log.e(TAG,"thread "+  Thread.currentThread().getId());
            }
        }).start();

      //  throw new RuntimeException("test");
        
    }
    
    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }
    
    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }
    
    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        
    }
}
