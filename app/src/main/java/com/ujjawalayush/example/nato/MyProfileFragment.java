package com.ujjawalayush.example.nato;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    View MyProfile;
    TextView No;
    RecyclerView recyclerView;
    RecyclerAdapter10 mAdapter;
    ArrayList<RecyclerData10> myList=new ArrayList<>();
    FirebaseAuth firebaseAuth;
    LinearLayoutManager linearLayoutManager;
    FirebaseUser user;
    FloatingActionButton floatingActionButton;
    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyProfile= inflater.inflate(R.layout.fragment_my_profile, container, false);
        No=(TextView)MyProfile.findViewById(R.id.t);
        recyclerView=(RecyclerView)MyProfile.findViewById(R.id.r);
        linearLayoutManager=new LinearLayoutManager(MyProfile.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter10(myList);
        recyclerView.setAdapter(mAdapter);
        floatingActionButton =(FloatingActionButton)MyProfile.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=MyProfile.getContext();
                Intent data=new Intent(context,Add.class);
                startActivity(data);
            }
        });
        myList.clear();
        mAdapter.notifyData(myList);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        DBAdapter db=new DBAdapter(MyProfile.getContext());
        db.open();
        Cursor c=db.getAllTrips();
        c.moveToFirst();
        if(c.getCount()>0){
            No.setVisibility(View.INVISIBLE);
            while(!c.isAfterLast()){
                String type=c.getString(1);
                String date=c.getString(2);
                String name=c.getString(3);
                String add=c.getString(4);
                String status=c.getString(5);
                String expected=c.getString(6);
                String member=c.getString(7);
                RecyclerData10 mLog=new RecyclerData10();
                mLog.setType(type);
                mLog.setDate(date);
                mLog.setName(name);
                mLog.setAdd(add);
                mLog.setStatus(status);
                mLog.setExpected(expected);
                mLog.setFragment("0");

                mLog.setMember(member);
                myList.add(mLog);
                mAdapter.notifyData(myList);
                c.moveToNext();
            }
        }
        else{
            No.setVisibility(View.VISIBLE);
        }
        recyclerView.scrollToPosition(myList.size() - 1);
        return MyProfile;
    }

}
