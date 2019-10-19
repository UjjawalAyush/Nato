package com.ujjawalayush.example.nato;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ExpenseFragment extends Fragment {


    public ExpenseFragment() {
        // Required empty public constructor
    }
    View expense;
    RecyclerView recyclerView,recyclerView1;
    Button b;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter16 mAdapter;
    RecyclerAdapter91 mAdapter1;
    ArrayList<RecyclerData16> myList=new ArrayList<>();
    String trip;
    ArrayList<RecyclerData91> myList1=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        expense= inflater.inflate(R.layout.fragment_expense, container, false);
        b=(Button)expense.findViewById(R.id.bu);
        trip=getArguments().getString("trip");
        recyclerView1=(RecyclerView)expense.findViewById(R.id.rrr);
        recyclerView=(RecyclerView)expense.findViewById(R.id.rr);
        linearLayoutManager=new LinearLayoutManager(expense.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter16(myList);
        recyclerView.setAdapter(mAdapter);
        mAdapter1=new RecyclerAdapter91(myList1);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(expense.getContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);
        RecyclerData16 mLog=new RecyclerData16();
        mLog.setCategory("Food");
        recyclerView1.setAdapter(mAdapter1);
        DBAdapter db=new DBAdapter(expense.getContext());
        db.open();
        Cursor c=db.getAllTrips();
        Cursor d=db.getAllMembers();
        d.moveToFirst();
        while(!d.isAfterLast()){
            if(d.getString(1).equals(trip)){
                RecyclerData91 mLog3=new RecyclerData91();
                mLog3.setValue(d.getString(2));
                mLog3.setDisplay(d.getString(3));
                myList1.add(mLog3);
                mAdapter1.notifyData(myList1);
            }
            d.moveToNext();
        }
        mLog.setDate("16/05/2019 12:32:32");
        mLog.setDescription("Chai Stall");
        mLog.setMoney("1290");
        mLog.setMembers("2");
        myList.add(mLog);
        mAdapter.notifyData(myList);
        RecyclerData16 mLog1=new RecyclerData16();
        mLog1.setCategory("Food");
        mLog1.setDate("16/05/2019 17:32:32");
        mLog1.setDescription("restaurant");
        mLog1.setMoney("100");
        mLog1.setMembers("2");
        myList.add(mLog1);
        mAdapter.notifyData(myList);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(expense.getContext(),Expense.class);
                data.putExtra("trip",trip);
                startActivity(data);
            }
        });
        return expense;
    }
}
