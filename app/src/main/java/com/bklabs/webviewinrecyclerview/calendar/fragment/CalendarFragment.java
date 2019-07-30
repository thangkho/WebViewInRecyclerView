package com.bklabs.webviewinrecyclerview.calendar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bklabs.webviewinrecyclerview.R;
import com.bklabs.webviewinrecyclerview.calendar.CalendarDialog;
import com.bklabs.webviewinrecyclerview.calendar.RoomChannel;
import com.bklabs.webviewinrecyclerview.calendar.customcalendar.AdapterCalendar;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarFragment extends Fragment implements AdapterCalendar.IClickItemCalendar {
    ArrayList<RoomChannel> mListRoom = new ArrayList<>();
    private int mNumberColumns = 7;
    private int mDaysCount = 35;
    private int mDaysOfWeek = 7;
    private int mWeekInMonth;
    private RecyclerView mRvCalendar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvCalendar = view.findViewById(R.id.rvNotificationF);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        initRvCalendar(calendar.getTime());
    }

    private void initRvCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // determine the cell for current month's beginning

//        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        //size of grid calendar
        mDaysCount = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //columns of gird calendar
        mWeekInMonth = mDaysCount % mDaysOfWeek == 0 ? mDaysCount / mDaysOfWeek : mDaysCount / mDaysOfWeek + 1;
        mDaysCount = mWeekInMonth * mDaysOfWeek;
        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // fill cells
        while (mListRoom.size() < mDaysCount) {
            RoomChannel scheduleCount = new RoomChannel();
            scheduleCount.setDate(calendar.getTime());
            mListRoom.add(scheduleCount);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        AdapterCalendar mAdapterCalendar = new AdapterCalendar(mListRoom, date, mWeekInMonth);
        mAdapterCalendar.setClickListener(this);
        mRvCalendar.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        mRvCalendar.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvCalendar.setLayoutManager(new GridLayoutManager(mRvCalendar.getContext(), mNumberColumns));
        mRvCalendar.setAdapter(mAdapterCalendar);

    }

    /**
     * get number day of previous month in grid calendar
     *
     * @param dayOfWeek value of day in Calendar
     * @return number day of previous month in grid calendar
     */
    private int getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            case Calendar.MONDAY:
            default:
                return 0;

        }
    }

    @Override
    public void clickItem(RoomChannel roomChannel, int with, int height, float x, float y, int[] test2) {
        CalendarDialog editNameDialogFragment = CalendarDialog.newInstance(roomChannel, with, height, x, y,test2);
        editNameDialogFragment.show(getChildFragmentManager(), "fragment_edit_name");
//        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View customView = layoutInflater.inflate(R.layout.fragment_edit_room,null);
//
//
//        PopupWindow  popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        //display the popup window
//        popupWindow.showAtLocation(mRvCalendar, Gravity.CENTER, (int)x, (int)y);
    }
}
