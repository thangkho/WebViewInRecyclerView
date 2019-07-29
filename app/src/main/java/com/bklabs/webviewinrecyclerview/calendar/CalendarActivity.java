package com.bklabs.webviewinrecyclerview.calendar;

import android.os.Bundle;

import com.bklabs.webviewinrecyclerview.R;
import com.bklabs.webviewinrecyclerview.calendar.fragment.CalendarFragment;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, CalendarFragment.newInstance(), "rageComicList")
                .commit();
    }
}
