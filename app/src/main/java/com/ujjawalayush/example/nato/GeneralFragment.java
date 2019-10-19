package com.ujjawalayush.example.nato;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralFragment extends Fragment {
    DatabaseReference databaseReference,databaseReference1;
    FirebaseUser user;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter14 mAdapter;
    ArrayList<RecyclerData14> myList=new ArrayList<>();
    public GeneralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View general=inflater.inflate(R.layout.fragment_general, container, false);
        recyclerView=(RecyclerView)general.findViewById(R.id.r1);
        linearLayoutManager=new LinearLayoutManager(general.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter14(myList);
        recyclerView.setAdapter(mAdapter);
        myList.clear();
        mAdapter.notifyData(myList);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        databaseReference1=FirebaseDatabase.getInstance().getReference().child("Posts").child(dataSnapshot1.getKey());
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.hasChild("Pictures")) {
                                    final String picture = dataSnapshot.child("Pictures").getValue().toString();
                                    DataSnapshot dataSnapshot2=dataSnapshot.child("Details");
                                    RecyclerData14 mLog=new RecyclerData14();
                                    if(dataSnapshot2.hasChild("EditText")) {
                                        final String editText = dataSnapshot2.child("EditText").getValue().toString();
                                        mLog.setEditText(editText);
                                    }
                                    final String username=dataSnapshot2.child("username").getValue().toString();
                                    final String uid=dataSnapshot2.child("uid").getValue().toString();
                                    final String time=dataSnapshot2.child("time").getValue().toString();
                                    mLog.setPictures(picture);
                                    mLog.setUsername(username);
                                    mLog.setUid(uid);
                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                                    Date resultdate = new Date(Long.parseLong(time));
                                    mLog.setTime(sdf.format(resultdate));
                                    myList.add(mLog);
                                    mAdapter.notifyData(myList);
                                    recyclerView.scrollToPosition(myList.size() - 1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return general;
    }
}
