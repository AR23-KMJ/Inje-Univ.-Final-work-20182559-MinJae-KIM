package com.kmj.sw_finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mBtnCalculator, mBtnCheckConsumption, mBtnGoodDayNews, mBtnCheckRequest, mBtnLogout;
    private ViewPager2 viewPager1, viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnCalculator = findViewById(R.id.btnCalculate);
        mBtnCheckConsumption = findViewById(R.id.btnCheckConsumption);
        mBtnGoodDayNews = findViewById(R.id.btnGoodDayNews);
        mBtnCheckRequest = findViewById(R.id.btnCheckRequest);
        mBtnLogout = findViewById(R.id.btn_logout1); // 추가

        viewPager1 = findViewById(R.id.viewPager1);
        viewPager2 = findViewById(R.id.viewPager2);

        int[] imageResourceIds = {
                R.drawable.gd1,
                R.drawable.gd2,
                R.drawable.gd3,
                R.drawable.gd4,
                R.drawable.gd5,
                R.drawable.gd6,
                R.drawable.gd7
        };

        viewPager1.setAdapter(new ImageAdapter(imageResourceIds));
        viewPager2.setAdapter(new ImageAdapter(imageResourceIds));

        mBtnCalculator.setOnClickListener(view -> startNewActivity(CalculatorActivity.class));
        mBtnCheckConsumption.setOnClickListener(view -> startNewActivity(DiaryActivity.class));
        mBtnGoodDayNews.setOnClickListener(view -> startNewActivity(NewsActivity.class));
        mBtnCheckRequest.setOnClickListener(view -> startNewActivity(RequestActivity.class));

        mBtnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startNewActivity(LoginActivity.class);
            finish();
        });
    }

    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

    private static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private final int[] imageResourceIds;

        public ImageAdapter(int[] imageResourceIds) {
            this.imageResourceIds = imageResourceIds;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(imageResourceIds[position]);
        }

        @Override
        public int getItemCount() {
            return imageResourceIds.length;
        }

        static class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                this.imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}