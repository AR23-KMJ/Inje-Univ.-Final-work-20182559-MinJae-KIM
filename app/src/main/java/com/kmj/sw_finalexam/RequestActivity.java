package com.kmj.sw_finalexam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestActivity extends AppCompatActivity {

    private EditText etStoreName, etRequest;
    private NumberPicker npGoodDayBoxes;
    private Button btnSubmitRequest;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        etStoreName = findViewById(R.id.etStoreName);
        etRequest = findViewById(R.id.etRequest);
        npGoodDayBoxes = findViewById(R.id.npGoodDayBoxes);
        btnSubmitRequest = findViewById(R.id.btnSubmitRequest);

        // 설정 가능한 박스의 최솟값 및 최댓값 설정
        NumberPicker numberPicker = findViewById(R.id.npGoodDayBoxes);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        // Firebase 데이터베이스 레퍼런스 설정
        databaseReference = FirebaseDatabase.getInstance().getReference("Requests");

        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest();
            }
        });
    }

    private void submitRequest() {
        String storeName = etStoreName.getText().toString().trim();
        String requestText = etRequest.getText().toString().trim();
        int goodDayBoxes = npGoodDayBoxes.getValue();

        if (storeName.isEmpty() || requestText.isEmpty()) {
            Toast.makeText(this, "가게 이름과 요청 사항을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase에 데이터 저장
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRequestsRef = databaseReference.child(userId);
        String requestId = "8aHA3dLtEdRwoAH97JMkxQB25j02";

        RequestModel requestModel = new RequestModel(storeName, requestText, goodDayBoxes);
        userRequestsRef.child(requestId).setValue(requestModel);

        Toast.makeText(this, "요청이 성공적으로 제출되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
