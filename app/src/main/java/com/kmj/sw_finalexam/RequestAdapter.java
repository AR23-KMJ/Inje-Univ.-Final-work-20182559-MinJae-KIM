package com.kmj.sw_finalexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private Context context;
    private List<RequestModel> requestList;

    public RequestAdapter(Context context, List<RequestModel> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel request = requestList.get(position);

        holder.storeNameTextView.setText(request.getStoreName());
        holder.requestTextView.setText(request.getRequestText());
        holder.goodDayBoxesTextView.setText("좋은데이 상자 수: " + request.getGoodDayBoxes());
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView storeNameTextView;
        public TextView requestTextView;
        public TextView goodDayBoxesTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeNameTextView = itemView.findViewById(R.id.storeNameTextView);
            requestTextView = itemView.findViewById(R.id.requestTextView);
            goodDayBoxesTextView = itemView.findViewById(R.id.goodDayBoxesTextView);
        }
    }
}
