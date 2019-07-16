package com.bklabs.webviewinrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.List;

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
        myListAdapter = new MyListAdapter(listData,displayMetrics.widthPixels);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        myList.setLayoutManager(layoutManager);
        myList.setAdapter(myListAdapter);
    }
}
