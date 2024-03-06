package com.kmj.sw_finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private Button mBtnLogout; // 수정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnCheck = findViewById(R.id.btn_Check);
        mBtnLogout = findViewById(R.id.btn_logout2);
        Button btnGoodDayNews = findViewById(R.id.btn_GoodDayNews);
        Button btnDiary2 = findViewById(R.id.btn_diary2);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CheckActivity.class);
                startActivity(intent);
            }
        });

        btnGoodDayNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        btnDiary2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DiaryActivity.class);
                startActivity(intent);
            }

        });



        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 처리 (Firebase 예시)
                FirebaseAuth.getInstance().signOut();

                // 로그인 화면으로 이동
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });
    }
}