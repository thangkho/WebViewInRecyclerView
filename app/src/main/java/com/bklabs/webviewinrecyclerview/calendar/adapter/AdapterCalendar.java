package com.bklabs.webviewinrecyclerview.calendar.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bklabs.webviewinrecyclerview.R;
import com.bklabs.webviewinrecyclerview.calendar.model.RoomChannel;
import com.bklabs.webviewinrecyclerview.calendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.ViewHolder> {

    private ArrayList<RoomChannel> mListScheduleCount;

    private Date mDate;
    private int mNumberRows;
    private IClickItemCalendar mClickListener;

    public AdapterCalendar(ArrayList<RoomChannel> days, Date date, int numberRows) {
        this.mListScheduleCount = days;
        this.mDate = date;
        this.mNumberRows = numberRows;
    }

    public void setData(ArrayList<RoomChannel> days, Date date, int numberRows) {
        this.mDate = date;
        this.mNumberRows = numberRows;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout view = (FrameLayout) LayoutInflater.from(parent.getContext())
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

        //update height item in grid calendar
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = CalendarUtils.setHeightItemCalendar(holder.itemView.getContext(), mNumberRows);
        holder.itemView.setLayoutParams(params);

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
        if (isSunday(scheduleCount.getDate())) {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setTextColor(Color.RED);
        } else if (isSaturday(scheduleCount.getDate())) {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setTextColor(Color.GREEN);

        } else if (day == currentDay && month == currentMonth && year == currentYear) {
            //if is today,
            holder.mTvDay.setTextColor(Color.BLACK);
            holder.mTvDay.setBackgroundColor(Color.GRAY);

        } else {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setTextColor(Color.BLACK);
        }
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

        ViewHolder(View itemView) {
            super(itemView);
            mTvDay = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null && getLayoutPosition() != RecyclerView.NO_POSITION) {
                int test2[] = new int[2];
                view.getLocationOnScreen(test2);
                mClickListener.clickItem(mListScheduleCount.get(getLayoutPosition()), view.getWidth(), view.getHeight(), view.getX(), view.getY(), test2);
            }
        }
    }

    //
    public void setClickListener(IClickItemCalendar iClickItemCalendar) {
        this.mClickListener = iClickItemCalendar;
    }

    public interface IClickItemCalendar {
        void clickItem(RoomChannel roomChannel, int with, int height, float x, float y, int[] test2);
    }

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