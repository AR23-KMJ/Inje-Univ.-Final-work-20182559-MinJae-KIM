package com.kmj.sw_finalexam;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // XML에서 PieChart 참조
        PieChart pieChart = findViewById(R.id.pieChart);

        // Intent에서 선택된 날짜 정보를 가져옴
        String selectedDate = getIntent().getStringExtra("selectedDate");

        if (selectedDate != null) {
            // Firebase 또는 다른 방법을 통해 데이터를 가져와 PieChart 업데이트
            updatePieChartWithFirebaseData(selectedDate, pieChart);
        } else {
            Toast.makeText(this, "날짜 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePieChartWithFirebaseData(String selectedDate, PieChart pieChart) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("results");

        databaseReference.orderByChild("selectedDate").equalTo(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<PieEntry> entries = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String resultText = snapshot.child("resultText").getValue(String.class);

                        float percentage = getPercentage(resultText, "대선");
                        entries.add(new PieEntry(percentage, "대선"));

                        percentage = getPercentage(resultText, "좋은데이");
                        entries.add(new PieEntry(percentage, "좋은데이"));

                        percentage = getPercentage(resultText, "진로");
                        entries.add(new PieEntry(percentage, "진로"));

                        percentage = getPercentage(resultText, "처음처럼");
                        entries.add(new PieEntry(percentage, "처음처럼"));
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "  의 하루 판촉 활동");
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(14f);

                    PieData pieData = new PieData(dataSet);
                    pieChart.setData(pieData);
                    pieChart.invalidate();
                } else {
                    Toast.makeText(ChartActivity.this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ChartActivity", "Error fetching data from Firebase: " + databaseError.getMessage());
                Toast.makeText(ChartActivity.this, "Firebase에서 데이터를 가져오는 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private float getPercentage(String resultText, String category) {
        String[] resultLines = resultText.split("\n");

        for (String line : resultLines) {
            if (line.contains(category)) {
                String percentageString = line.split(":")[1].trim().split("%")[0].trim();

                try {
                    return Float.parseFloat(percentageString);
                } catch (NumberFormatException e) {
                    Log.e("ChartActivity", "Error parsing percentage: " + e.getMessage());
                }
            }
        }

        return 0f;
    }
}