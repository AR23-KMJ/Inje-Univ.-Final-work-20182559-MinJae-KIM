package com.kmj.sw_finalexam;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    private List<RequestModel> requestList;
    private TextView storeNameTextView;
    private TextView requestTextView;
    private TextView goodDayBoxesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        requestList = new ArrayList<>();
        storeNameTextView = findViewById(R.id.storeNameTextView);
        requestTextView = findViewById(R.id.requestTextView);
        goodDayBoxesTextView = findViewById(R.id.goodDayBoxesTextView);

        // Firebase에서 특정 값에 대한 Requests 데이터 읽어오기
        String userKey = "v0OzRYdDHVPiSAMr5bKBVHlZw4w2"; // 유저 키값
        String specificKey = "8aHA3dLtEdRwoAH97JMkxQB25j02";
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requests").child(userKey).child(specificKey);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestList.clear(); // 리스트 초기화

                RequestModel request = dataSnapshot.getValue(RequestModel.class);
                if (request != null) {
                    Log.d("CheckActivity", "Received request: " + request.getStoreName() + ", " + request.getRequestText() + ", " + request.getGoodDayBoxes());
                    requestList.add(request);
                } else {
                    Log.e("CheckActivity", "Received null request. Snapshot key: " + dataSnapshot.getKey());
                }

                // 데이터 변경 후에 UI 업데이트
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리
                Log.e("CheckActivity", "Firebase database error: " + databaseError.getMessage());
            }
        });
    }

    // UI 업데이트 메서드
    private void updateUI() {
        StringBuilder storeNames = new StringBuilder();
        StringBuilder requestTexts = new StringBuilder();
        StringBuilder goodDayBoxes = new StringBuilder();

        for (RequestModel request : requestList) {
            storeNames.append("가게 이름: ").append(request.getStoreName()).append("\n");
            requestTexts.append("요청사항: ").append(request.getRequestText()).append("\n");
            goodDayBoxes.append("추가 주문 /박스: ").append(request.getGoodDayBoxes()).append("\n");
        }

        storeNameTextView.setText(storeNames.toString());
        requestTextView.setText(requestTexts.toString());
        goodDayBoxesTextView.setText(goodDayBoxes.toString());
    }
}