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
import android.widget.Toast;

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
public class ErrandFragment extends Fragment {


    public ErrandFragment() {
        // Required empty public constructor
    }

    View MyProfile;
    TextView No;
    RecyclerView recyclerView;
    RecyclerAdapter10 mAdapter;
    ArrayList<RecyclerData10> myList=new ArrayList<>();
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;
    FirebaseUser user;
    FloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyProfile= inflater.inflate(R.layout.fragment_errands, container, false);
        Toast.makeText(MyProfile.getContext(),"Click on the options to gather more info.",Toast.LENGTH_LONG).show();
        recyclerView=(RecyclerView)MyProfile.findViewById(R.id.rr);
        linearLayoutManager=new LinearLayoutManager(MyProfile.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter10(myList);
        recyclerView.setAdapter(mAdapter);
        myList.clear();
        mAdapter.notifyData(myList);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        final String name=dataSnapshot1.getKey();
                        final String expected=dataSnapshot1.child("Details").child("Expected").getValue().toString();
                        final String status=dataSnapshot1.child("Details").child("Status").getValue().toString();
                        final String members=dataSnapshot1.child("Details").child("members").getValue().toString();
                        final String date=dataSnapshot1.child("Details").child("date").getValue().toString();
                        final String description=dataSnapshot1.child("Details").child("description").getValue().toString();
                        final String type=dataSnapshot1.child("Details").child("type").getValue().toString();
                        final String add=dataSnapshot1.child("Details").child("Add").getValue().toString();

                        RecyclerData10 mLog=new RecyclerData10();
                        mLog.setType(type);
                        mLog.setDate(date);
                        mLog.setName(name);
                        mLog.setAdd(description);
                        mLog.setStatus(status);
                        mLog.setFragment("1");
                        mLog.setExpected(expected);
                        mLog.setMember(members);
                        myList.add(mLog);
                        mAdapter.notifyData(myList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        return MyProfile;
    }

}
