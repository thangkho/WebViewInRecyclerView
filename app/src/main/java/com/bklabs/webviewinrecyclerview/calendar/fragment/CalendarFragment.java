package com.bklabs.webviewinrecyclerview.calendar.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bklabs.webviewinrecyclerview.R;
import com.bklabs.webviewinrecyclerview.calendar.adapter.AdapterCalendar;
import com.bklabs.webviewinrecyclerview.calendar.dialog.CalendarDialog;
import com.bklabs.webviewinrecyclerview.calendar.model.RoomChannel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
    Calendar calendarShow = Calendar.getInstance();
    private ImageView imgBack;
    private ImageView imgNext;
    private TextView tvTime;
    private AdapterCalendar mAdapterCalendar;
    private LinearLayout rootView;

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
        rootView = view.findViewById(R.id.root_view);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        imgBack = view.findViewById(R.id.back);
        imgNext = view.findViewById(R.id.next);
        tvTime = view.findViewById(R.id.tvTime);
        initRvCalendar(calendarShow.getTime(), false);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarShow.add(Calendar.MONTH, -1);
                initRvCalendar(calendarShow.getTime(), true);
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarShow.add(Calendar.MONTH, 1);
                initRvCalendar(calendarShow.getTime(), true);
            }
        });
    }

    private void initRvCalendar(Date date, boolean isUpdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String month = new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)];
        tvTime.setText(month + "/" + calendar.get(Calendar.YEAR));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // determine the cell for current month's beginning

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        //size of grid calendar
        mDaysCount = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //columns of gird calendar
        mWeekInMonth = mDaysCount % mDaysOfWeek == 0 ? mDaysCount / mDaysOfWeek : mDaysCount / mDaysOfWeek + 1;
        mDaysCount = mWeekInMonth * mDaysOfWeek;
        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // fill cells
        mListRoom.clear();
        while (mListRoom.size() < mDaysCount) {
            RoomChannel scheduleCount = new RoomChannel();
            scheduleCount.setDate(calendar.getTime());
            mListRoom.add(scheduleCount);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (isUpdate) {

            Log.d("tag_calendar", mListRoom.size() + " " + mWeekInMonth + " " + date.toString());
            mAdapterCalendar.setData(mListRoom, date, mWeekInMonth);
            mAdapterCalendar.notifyDataSetChanged();
        } else {
            mAdapterCalendar = new AdapterCalendar(mListRoom, date, mWeekInMonth);
            mAdapterCalendar.setClickListener(this);
            mRvCalendar.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.HORIZONTAL));
            mRvCalendar.addItemDecoration(new DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL));
            mRvCalendar.setLayoutManager(new GridLayoutManager(mRvCalendar.getContext(), mNumberColumns));
            mRvCalendar.setAdapter(mAdapterCalendar);
        }

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
        CalendarDialog editNameDialogFragment = CalendarDialog.newInstance(roomChannel, with, height, x, y, test2);
        editNameDialogFragment.show(getChildFragmentManager(), "fragment_edit_name");
    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
            int contentViewTop = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());

            if (heightDiff <= contentViewTop) {
                Toast.makeText(getActivity(), "hide keyboard ", Toast.LENGTH_SHORT).show();
//                onHideKeyboard();
//
//                Intent intent = new Intent("KeyboardWillHide");
//                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                Toast.makeText(getActivity(), "show keyboard " + keyboardHeight, Toast.LENGTH_SHORT).show();

//                onShowKeyboard(keyboardHeight);
//
//                Intent intent = new Intent("KeyboardWillShow");
//                intent.putExtra("KeyboardHeight", keyboardHeight);
//                broadcastManager.sendBroadcast(intent);
            }
        }
    };
}
