package com.kmj.sw_finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000; // 3 초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(() -> {
            // 메인 액티비티 시작
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            // 스플래시 액티비티를 종료하여 다시 돌아가지 않도록 합니다.
            finish();
        }, SPLASH_TIMEOUT);
    }
}
