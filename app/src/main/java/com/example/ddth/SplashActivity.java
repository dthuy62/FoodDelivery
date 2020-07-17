package com.example.ddth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ddth.Login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new  Handler();
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Splash);
        setContentView(R.layout.activity_splash);
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

       timer.schedule(new TimerTask() {
           @Override
           public void run() {
               Intent intent = new Intent(SplashActivity.this, MainActivity.class);
               startActivity(intent);
               finish();
           }
       },3000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}