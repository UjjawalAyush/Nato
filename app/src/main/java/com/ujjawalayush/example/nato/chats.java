package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class chats extends AppCompatActivity {
    String username,uid;
    RecyclerView recyclerView;
    Toolbar toolbar;
    FirebaseUser user;
    ProgressDialog progressDialog;
    RecyclerAdapter4 mAdapter;
    TextView textView;
    EditText editText;
    ArrayList<RecyclerData4> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatmain);
        Intent data=getIntent();
        uid=data.getBundleExtra("extras").getString("uid");
        username=data.getBundleExtra("extras").getString("username");

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView1111);
        toolbar=(Toolbar)findViewById(R.id.toolbar1999);
        toolbar.setTitle(username);
        progressDialog=new ProgressDialog(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter4(myList);
        editText=(EditText)findViewById(R.id.send);
        recyclerView.setAdapter(mAdapter);
        user=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uid);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String k=dataSnapshot.getKey();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uid);
                databaseReference.child(k).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DBAdapter db=new DBAdapter(chats.this);
                        db.open();
                        final String message=dataSnapshot.child("Message").getValue().toString();
                        final String time=dataSnapshot.child("Time").getValue().toString();
                        long id=db.insertContact3(message,time,username,uid,"0");
                        RecyclerData4 mLog=new RecyclerData4();
                        mLog.setStatus("0");
                        mLog.setDate(time);
                        mLog.setMessage(message);
                        mLog.setUid(uid);
                        mLog.setId(id);
                        mLog.setUsername(username);
                        myList.add(mLog);
                        mAdapter.notifyData(myList,1);
                        Cursor cw = db.getAllContacts2();
                        final long uid2;
                        String uid1 = "-1";
                        cw.moveToFirst();
                        if (cw.getCount() > 0) {
                            while (!cw.isAfterLast()) {
                                if (cw.getString(4).equals(uid)) {
                                    uid1 = cw.getString(0);
                                    break;
                                }
                                cw.moveToNext();
                            }

                        }
                        uid2 = Long.parseLong(uid1);
                        if(uid2!=-1) {
                            db.updateContact2(uid2,message,time, username, uid,"p");
                        }
                        else{
                            long id1 = db.insertContact2(message,time,username,uid,"p");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllContacts3();
        c.moveToFirst();
        if(c.getCount()>0) {
            while (!c.isAfterLast()) {
                final String uid1 = c.getString(4);
                if(uid1.equals(uid)) {
                    String x = c.getString(1);
                    final String date1 = c.getString(2);
                    String status = c.getString(5);
                    RecyclerData4 mLog = new RecyclerData4();
                    mLog.setStatus(status);
                    mLog.setDate(date1);
                    mLog.setUsername(username);
                    mLog.setUid(uid);
                    mLog.setMessage(x);
                    mLog.setId(Long.parseLong(c.getString(0)));

                    myList.add(mLog);
                    mAdapter.notifyData(myList,1);
                }
                c.moveToNext();

            }
        }
        recyclerView.scrollToPosition(myList.size() - 1);

    }
    public void onClick(View v){
        String edit=editText.getText().toString().trim();
        DBAdapter db=new DBAdapter(this);
        editText.setText("");
        String time=Long.toString(System.currentTimeMillis());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        final String formattedTime=dateFormat.format(date);
        db.open();
        long id=db.insertContact3(edit,formattedDate+" "+formattedTime,username,uid,"1");
        RecyclerData4 mLog=new RecyclerData4();
        mLog.setStatus("1");
        mLog.setDate(formattedDate+" "+formattedTime);
        mLog.setMessage(edit);
        mLog.setId(id);
        mLog.setUid(uid);
        mLog.setUsername(username);
        myList.add(mLog);
        mAdapter.notifyData(myList,1);
        Cursor cw = db.getAllContacts2();
        final long uid2;
        String uid1 = "-1";
        cw.moveToFirst();
        if (cw.getCount() > 0) {
            while (!cw.isAfterLast()) {
                if (cw.getString(4).equals(uid)) {
                    uid1 = cw.getString(0);
                    break;
                }
                cw.moveToNext();
            }

        }
        uid2 = Long.parseLong(uid1);
        if(uid2!=-1) {
            db.updateContact2(uid2, edit,formattedTime+" "+formattedDate, username, uid,"p");
        }
        else{
            long id1 = db.insertContact2(edit,formattedTime+" "+formattedDate,username,uid,"p");

        }
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat").child(uid).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child(time).child("Message").setValue(edit);
        databaseReference.child(time).child("Status").setValue("Received");
        databaseReference.child(time).child("Time").setValue(formattedDate+" "+formattedTime);
        recyclerView.scrollToPosition(myList.size() - 1);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        super.onBackPressed();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uid);
        databaseReference.removeValue();
    }
}