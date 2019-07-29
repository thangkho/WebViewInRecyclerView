package com.bklabs.webviewinrecyclerview.calendar.customcalendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bklabs.webviewinrecyclerview.R;
import com.bklabs.webviewinrecyclerview.calendar.CalendarUtils;
import com.bklabs.webviewinrecyclerview.calendar.RoomChannel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.ViewHolder> {

    private final ArrayList<RoomChannel> mListScheduleCount;

    private Date mDate;
    private int mNumberRows;

    public AdapterCalendar(ArrayList<RoomChannel> days, Date date, int numberRows) {
        this.mListScheduleCount = days;
        this.mDate = date;
        this.mNumberRows = numberRows;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_item, parent, false);
        //update height item in grid calendar
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = CalendarUtils.setHeightItemCalendar(view.getContext(), mNumberRows);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomChannel scheduleCount = mListScheduleCount.get(position);
        Calendar cal = Calendar.getInstance();
        // get value of current day
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        // get value of day in cell
        cal.setTime(scheduleCount.getDate());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.setTime(mDate);
        // if days of another month
        String color = "";
        if (isSunday(scheduleCount.getDate())) {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setTextColor(Color.RED);
            color = "sunday";
        } else if (isSaturday(scheduleCount.getDate())) {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setTextColor(Color.GREEN);
            color = "calendar_color_saturday";
        } else if (day == currentDay && month == currentMonth && year == currentYear) {
            //if is today,
            holder.mTvDay.setTextColor(Color.BLACK);
            holder.mTvDay.setBackgroundColor(Color.GRAY);
            color = "today";
        } else {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setTextColor(Color.BLACK);
        }
//        if (scheduleCount.getCount() > 0) {
//            if (year < currentYear || (year == currentYear && month < currentMonth)
//                    || (month == currentMonth && year == currentYear && day < currentDay)) {
//                holder.mTvTotalEvents.setBackground(holder.mTvTotalEvents.getContext().getResources()
//                        .getDrawable(R.drawable.bgr_layout_total_disable, null));
//                holder.mTvTotalEvents.setTextColor(Color.WHITE);
//            } else {
//                holder.mTvTotalEvents.setBackground(holder.mTvTotalEvents.getContext().getResources()
//                        .getDrawable(R.drawable.bgr_layout_total, null));
//                holder.mTvTotalEvents.setTextColor(holder.mTvTotalEvents.getResources().getColor(R.color.background, null));
//            }
//            holder.mTvTotalEvents.setText(String.valueOf(scheduleCount.getCount()));
//        } else {
//            holder.mTvTotalEvents.setVisibility(View.INVISIBLE);
//        }
        holder.mTvDay.setText(String.valueOf(day));
    }

    @Override
    public int getItemCount() {
        return mListScheduleCount == null ? 0 : mListScheduleCount.size();
    }

    /**
     * view holder item of grid day
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvDay;
        TextView mTvTotalEvents;

        ViewHolder(View itemView) {
            super(itemView);
            mTvDay = itemView.findViewById(R.id.tv_date);
            mTvTotalEvents = itemView.findViewById(R.id.tv_total_schedule);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null && getLayoutPosition() != RecyclerView.NO_POSITION) {
//                mClickListener.onItemClick(mListScheduleCount.get(getLayoutPosition()));
//            }
        }
    }
//
//    public void setClickListener(IClickItemCalendar iClickItemCalendar) {
//        this.mClickListener = iClickItemCalendar;
//    }

    private boolean isSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    private boolean isSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }
}