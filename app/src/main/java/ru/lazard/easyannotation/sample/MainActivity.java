package ru.lazard.easyannotation.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ru.lazard.easyannotation.annotations.Async;
import ru.lazard.easyannotation.annotations.LogMethod;
import ru.lazard.easyannotation.annotations.NotNull;
import ru.lazard.easyannotation.annotations.Safe;
import ru.lazard.easyannotation.annotations.Sync;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "easyannotation";//MainActivity.class.getSimpleName();

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick " + v + " thread=" + Thread.currentThread().getId());
        switch (v.getId()) {
            case R.id.button1:
                button1(v);
                break;
            case R.id.button2:
                button2(v);
                break;
            case R.id.button3:
                button3(v);
                break;
            case R.id.button4:
                button4(v);
                break;
            case R.id.button5:
                button5(v);
                break;
            case R.id.button6:
                button6(v);
                break;
            case R.id.button7:
                button7(v);
                break;
            case R.id.button8:
                button8(v);
                break;
            case R.id.button9:
                button9(null);
                break;
            case R.id.button10:
                button10(v);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button10).setOnClickListener(this);
    }

    @Safe
    private void button1(View v) {
        Log.e(TAG, "button1 " + v + " thread=" + Thread.currentThread().getId());
        throw new RuntimeException("test");
    }

    @Safe
    @Async
    private void button2(View v) {
        Log.e(TAG, "button2 " + v + " thread=" + Thread.currentThread().getId());
        throw new RuntimeException("test");
    }

    @Async
    private void button3(View v) {
        Log.e(TAG, "button3 " + v + " thread=" + Thread.currentThread().getId());
    }

    @Safe
    @Sync
    private void button4(View v) {
        Log.e(TAG, "button4 " + v + " thread=" + Thread.currentThread().getId());
        throw new RuntimeException("test");
    }

    @Sync
    private void button5(View v) {
        Log.e(TAG, "button5 " + v + " thread=" + Thread.currentThread().getId());
    }

    @Sync(delay = 1000)
    private void button6(View v) {
        Log.e(TAG, "button6 " + v + " thread=" + Thread.currentThread().getId());
    }

    @LogMethod
    private void button7(View v) {
        Log.e(TAG, "button7 " + v + " thread=" + Thread.currentThread().getId());
    }

    @NotNull
    private void button8(View v) {
        Log.e(TAG, "button8 " + v + " thread=" + Thread.currentThread().getId());
    }

    @NotNull
    private void button9(View v) {
        Log.e(TAG, "button9 " + v + " thread=" + Thread.currentThread().getId());
    }

    @Safe
    @NotNull
    private void button10(View v) {
        Log.e(TAG, "button10 " + v + " thread=" + Thread.currentThread().getId());
        throw new RuntimeException("test");
    }


}
