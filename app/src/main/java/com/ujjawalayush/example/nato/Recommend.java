package com.ujjawalayush.example.nato;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class Recommend extends AppCompatActivity {
    String time,trip;
    RecyclerView recyclerView;
    RecyclerAdapter18 mAdapter;
    ArrayList<RecyclerData18> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);
        recyclerView=(RecyclerView)findViewById(R.id.rr);
        Intent data=getIntent();
        trip=data.getBundleExtra("extras").getString("trip");
        time=data.getBundleExtra("extras").getString("time");
        mAdapter=new RecyclerAdapter18(myList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1234);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        RecyclerData18 mLog=new RecyclerData18();
        mLog.setRating("3.0");
        mLog.setName("Uioj");
        mLog.setComment("Good Idea");
        myList.add(mLog);
        mAdapter.notifyData(myList);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent data=new Intent(Recommend.this,Trip.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
}
