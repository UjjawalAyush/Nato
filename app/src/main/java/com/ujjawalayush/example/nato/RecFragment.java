package com.ujjawalayush.example.nato;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecFragment extends Fragment {


    public RecFragment() {
        // Required empty public constructor
    }

    View rec;
    String trip;
    RecyclerAdapter17 mAdapter;
    ArrayList<RecyclerData17> myList=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rec= inflater.inflate(R.layout.fragment_rec, container, false);
        trip=getArguments().getString("trip");
        recyclerView=(RecyclerView)rec.findViewById(R.id.r);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rec.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter17(myList);
        recyclerView.setAdapter(mAdapter);
        RecyclerData17 mLog=new RecyclerData17();
        mLog.setStartingDate("21-03-2000");
        mLog.setEndingDate("21-03-2000");
        mLog.setName("Dehradun");
        mLog.setBy("Ujjawal");
        mLog.setTrip(trip);
        mLog.setX("1");
        mLog.setTime(Long.toString(System.currentTimeMillis()));
        mLog.setDescription("Birthday Party");
        myList.add(mLog);
        mAdapter.notifyData(myList);
        return rec;
    }
}
