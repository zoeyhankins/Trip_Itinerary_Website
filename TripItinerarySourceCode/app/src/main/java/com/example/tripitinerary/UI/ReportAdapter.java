package com.example.tripitinerary.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripitinerary.entities.Vacation;
import com.example.tripitinerary.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final Context context;
    private final List<Vacation> vacationList;

    public ReportAdapter(Context context, List<Vacation> vacationList) {
        this.context = context;
        this.vacationList = vacationList;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        Vacation vacation = vacationList.get(position);
        holder.titleView.setText(vacation.getTitle());
        holder.hotelView.setText(vacation.getHotel());
        holder.dateRangeView.setText(vacation.getStartDate() + " - " + vacation.getEndDate());
    }

    @Override
    public int getItemCount() {
        return vacationList.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView;
        final TextView hotelView;
        final TextView dateRangeView;

        public ReportViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.reportTitleView);
            hotelView = itemView.findViewById(R.id.reportHotelView);
            dateRangeView = itemView.findViewById(R.id.reportDateRangeView);
        }
    }
}
