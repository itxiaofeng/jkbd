package cn.android.jkbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.android.jkbd.R;

/**
 * Created by Administrator on 2017/6/27.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        countDownTimer.start();
    }
    CountDownTimer countDownTimer = new CountDownTimer(4000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();
        }
    };
}
