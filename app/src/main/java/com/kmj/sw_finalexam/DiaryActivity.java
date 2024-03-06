package com.kmj.sw_finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView resultTextView;
    private CalendarView calendarView;
    private Button btnToChart; // 버튼 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        resultTextView = findViewById(R.id.resultTextView);
        calendarView = findViewById(R.id.calendarView);
        btnToChart = findViewById(R.id.btnToChart); // 버튼 초기화

        // Firebase 데이터베이스 레퍼런스 설정
        databaseReference = FirebaseDatabase.getInstance().getReference("results");

        // 달력에서 날짜를 선택했을 때 이벤트 처리
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 날짜로 날짜 문자열 생성
                String selectedDate = String.format(Locale.getDefault(), "날짜: %04d-%02d-%02d", year, month + 1, dayOfMonth);
                fetchDataFromFirebase(selectedDate);
            }
        });

        // btnToChart 버튼 클릭 이벤트 처리
        btnToChart.setOnClickListener(view -> {
            // 선택된 날짜로 ChartActivity로 이동하는 코드
            Intent intent = new Intent(DiaryActivity.this, ChartActivity.class);
            String currentDate = new SimpleDateFormat("날짜: yyyy-MM-dd", Locale.getDefault()).format(new Date());
            intent.putExtra("selectedDate", currentDate);
            startActivity(intent);
        });

        // 현재 날짜를 가져와서 Firebase에서 해당 날짜의 데이터를 가져옴
        String currentDate = new SimpleDateFormat("날짜: yyyy-MM-dd", Locale.getDefault()).format(new Date());
        fetchDataFromFirebase(currentDate);
    }

    private void fetchDataFromFirebase(String selectedDate) {
        Log.d("DiaryActivity", "Fetching data for date: " + selectedDate);
        databaseReference.orderByChild("selectedDate").equalTo(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String resultText = snapshot.child("resultText").getValue(String.class);
                        Float value = snapshot.child("value").getValue(Float.class); // 변경된 부분

                        if (resultText != null) {
                            Log.d("DiaryActivity", "Fetched resultText: " + resultText);

                            // 수정된 부분 시작
                            if (value != null) {
                                // value 값이 null이 아니면 해당 값을 사용
                                Log.d("DiaryActivity", "Fetched value: " + value);
                            } else {
                                // value 값이 null이면 기본값 사용
                                value = 0.0f;
                                Log.e("DiaryActivity", "Error: value not found for date: " + selectedDate);
                            }
                            // 수정된 부분 끝

                            resultTextView.setText(resultText);
                            return; // 데이터를 찾았으면 종료
                        }
                    }
                    // 데이터는 있지만 resultText를 찾지 못한 경우
                    Log.e("DiaryActivity", "Error: resultText not found for date: " + selectedDate);
                    resultTextView.setText("데이터가 없습니다.");
                } else {
                    // 데이터가 없을 때의 처리
                    Log.e("DiaryActivity", "No data found for date: " + selectedDate);
                    resultTextView.setText("이 날에 지정하신 데이터가 없습니다.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 가져오기를 취소했을 때의 처리
                Log.e("DiaryActivity", "Error fetching data from Firebase: " + databaseError.getMessage());
                resultTextView.setText("파이어베이스에서의 에러가 발생하였습니다.");
            }
        });
    }
}