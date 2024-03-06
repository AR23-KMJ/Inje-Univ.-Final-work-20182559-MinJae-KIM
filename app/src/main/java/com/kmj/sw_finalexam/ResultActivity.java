package com.kmj.sw_finalexam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.security.identity.ResultData;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ResultActivity extends AppCompatActivity {

    private int visitOffice;
    private int totalTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getResultValues();

        // 결과를 나타내는 TextView를 찾음
        TextView resultTextView = findViewById(R.id.resultTextView);

        // 날짜를 나타내는 TextView를 찾음
        TextView dateTextView = findViewById(R.id.dateTextView);

        // 결과를 나타내는 텍스트 생성
        @SuppressLint("DefaultLocale") String resultText = String.format("총 방문업소: %d곳\n총 테이블: %d 테이블\n\n대선: %.1f%%\n좋은데이: %.1f%%\n처음처럼: %.1f%%\n진로: %.1f%%\n\n전환: %d병\n추가주문: %d병",
                visitOffice, totalTable, getIntent().getDoubleExtra("presidentialPercent", 0),
                getIntent().getDoubleExtra("goodDayPercent", 0),
                getIntent().getDoubleExtra("chumChurumPercent", 0),
                getIntent().getDoubleExtra("hiteSojuPercent", 0),
                getIntent().getIntExtra("presidentialPromotion", 0),
                getIntent().getIntExtra("hiteJinroPromotion", 0));

        // TextView에 결과를 표시
        resultTextView.setText(resultText);

        // 날짜 값을 나타내는 텍스트 생성
        String dateText = String.format("날짜: %s", getIntent().getStringExtra("selectedDate"));

        // TextView에 날짜 값을 표시
        dateTextView.setText(dateText);

        // 버튼을 찾고 클릭 이벤트 리스너를 설정
        Button sendResultButton = findViewById(R.id.btn_SendResult);
        sendResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirebase(dateTextView.getText().toString(), resultTextView.getText().toString());
            }
        });

        Button btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티를 종료합니다.
            }
        });
    }

    private void getResultValues() {
        double result = getIntent().getDoubleExtra("result", 0);
        visitOffice = getIntent().getIntExtra("visitOffice", 0);
        totalTable = getIntent().getIntExtra("totalTable", 0);
    }

    private void saveDataToFirebase(String date, String result) {
        // Firebase에 데이터를 저장하기 위한 작업
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("results"); // "results"는 데이터베이스의 경로입니다.

        // 데이터 객체 생성
        com.kmj.sw_finalexam.ResultData resultData = new com.kmj.sw_finalexam.ResultData(date, result);

        // Firebase에 데이터 추가
        String key = myRef.push().getKey(); // 고유한 키 생성
        myRef.child(key).setValue(resultData);

        // 저장 완료 메시지 또는 원하는 처리를 추가할 수 있습니다.
        Toast.makeText(this, "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
}