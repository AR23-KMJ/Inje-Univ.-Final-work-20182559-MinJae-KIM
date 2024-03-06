package com.kmj.sw_finalexam;

// CalculatorActivity.java

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class CalculatorActivity extends AppCompatActivity {

    private EditText etVisitOffice, etTotalTable, etPresidential, etGoodDay, etHiteSoju, etChumChurum, etPresidentialPromotion, etHiteJinroPromotion;
    private Button dateButton;

    // 추가: 각 항목의 백분율을 저장할 변수들
    private double presidentialPercent, goodDayPercent, hiteSojuPercent, chumChurumPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        etVisitOffice = findViewById(R.id.etVisitOffice);
        etTotalTable = findViewById(R.id.etTotalTable);
        etPresidential = findViewById(R.id.etPresidential);
        etGoodDay = findViewById(R.id.etGoodDay);
        etHiteSoju = findViewById(R.id.etHiteSoju);
        etChumChurum = findViewById(R.id.etChumChurum);
        etPresidentialPromotion = findViewById(R.id.etPresidentialPromotion);
        etHiteJinroPromotion = findViewById(R.id.etHiteJinroPromotion);

        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateAndSave();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // 선택된 날짜를 처리하는 코드 추가
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateButton.setText(selectedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    private void calculateAndSave() {
        try {
            // 각 항목에 대한 값을 가져와서 계산
            int visitOffice = Integer.parseInt(etVisitOffice.getText().toString());
            int totalTable = Integer.parseInt(etTotalTable.getText().toString());
            double presidential = Double.parseDouble(etPresidential.getText().toString());
            double goodDay = Double.parseDouble(etGoodDay.getText().toString());
            double hiteSoju = Double.parseDouble(etHiteSoju.getText().toString());
            double chumChurum = Double.parseDouble(etChumChurum.getText().toString());

            int presidentialPromotion = Integer.parseInt(etPresidentialPromotion.getText().toString());
            int hiteJinroPromotion = Integer.parseInt(etHiteJinroPromotion.getText().toString());

            // 추가: totalTable이 0이면 계산을 수행하지 않고 예외처리
            if (totalTable == 0) {
                Toast.makeText(this, "총 테이블은 0이 될 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 각 항목의 백분율 값을 활용한 계산
            double presidentialPercent = (presidential / totalTable) * 100;
            double goodDayPercent = (goodDay / totalTable) * 100;
            double hiteSojuPercent = (hiteSoju / totalTable) * 100;
            double chumChurumPercent = (chumChurum / totalTable) * 100;

            // 계산 로직: 각 항목의 백분율 값을 활용한 계산
            double totalPercent = presidentialPercent + goodDayPercent + hiteSojuPercent + chumChurumPercent;

            // 각 항목의 백분율 값을 다시 계산하여 합이 100이 되도록 보정
            presidentialPercent = (presidentialPercent / totalPercent) * 100;
            goodDayPercent = (goodDayPercent / totalPercent) * 100;
            hiteSojuPercent = (hiteSojuPercent / totalPercent) * 100;
            chumChurumPercent = (chumChurumPercent / totalPercent) * 100;

            // 계산된 결과를 저장하는 로직 추가
            String selectedDate = dateButton.getText().toString();
            double result = totalPercent / 4;
            saveResult(selectedDate, result, visitOffice, totalTable, presidentialPromotion, hiteJinroPromotion, presidentialPercent, goodDayPercent, hiteSojuPercent, chumChurumPercent);

            // 결과를 다음 액티비티로 전달
            Intent intent = new Intent(CalculatorActivity.this, ResultActivity.class);
            intent.putExtra("result", result);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("visitOffice", visitOffice);
            intent.putExtra("totalTable", totalTable);
            intent.putExtra("presidentialPromotion", presidentialPromotion);
            intent.putExtra("hiteJinroPromotion", hiteJinroPromotion);
            intent.putExtra("presidentialPercent", presidentialPercent);
            intent.putExtra("goodDayPercent", goodDayPercent);
            intent.putExtra("hiteSojuPercent", hiteSojuPercent);
            intent.putExtra("chumChurumPercent", chumChurumPercent);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveResult(String date, double result, int visitOffice, int totalTable, int presidentialPromotion, int hiteJinroPromotion,
                            double presidentialPercent, double goodDayPercent, double hiteSojuPercent, double chumChurumPercent) {

        @SuppressLint("DefaultLocale") String resultText = String.format("날짜: %s\n총 방문업소: %d\n총 테이블: %d\n\n대선: %.1f%%\n좋은데이: %.1f%%\n처음처럼: %.1f%%\n진로: %.1f%%\n\n대선판촉: %d명\n하이트진로판촉: %d명",
                date, visitOffice, totalTable, presidentialPercent, goodDayPercent, chumChurumPercent, hiteSojuPercent, presidentialPromotion, hiteJinroPromotion);
        // 여기에서 resultText를 사용하거나, 원하는 대로 결과를 처리하세요.
        // 예를 들어, 텍스트뷰에 결과를 설정하거나 데이터베이스에 저장하는 등의 작업을 수행할 수 있습니다.
    }
}