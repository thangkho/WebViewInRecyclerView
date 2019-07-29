package com.bklabs.webviewinrecyclerview;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView myList;
    MyListAdapter myListAdapter;
    List<ItemData> listData = Datas.createData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = findViewById(R.id.myList);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        myListAdapter = new MyListAdapter(listData, displayMetrics.widthPixels);

        PreCachingLayoutManager layoutManager = new PreCachingLayoutManager(this);
        layoutManager.setOrientation(
                RecyclerView.VERTICAL);
        layoutManager.setStackFromEnd(true);
        myList.setLayoutManager(layoutManager);
        myList.setAdapter(myListAdapter);
        myList.setItemViewCacheSize(0);
        Log.d("timecreate", getDayChatTimeLine("1563528889801"));
    }

    public String getDayChatTimeLine(String created) {
        try {
            Timestamp stamp = new Timestamp(Long.valueOf(created));
            Date date = new Date(stamp.getTime());
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            calendar.setTime(date);
            String month = new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)];
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int year = calendar.get(Calendar.YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            return hour + " " + min + " " + month + " " + day + ", " + year;//(year == currentYear ? "" : ", " + year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
