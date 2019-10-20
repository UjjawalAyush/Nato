package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Friends extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<RecyclerData3> arrayList=new ArrayList<>();
    String username;
    FirebaseUser user;
    Cursor c;
    byte[] byteBitmap;
    DBAdapter db;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter3 recyclerAdapter3;
    SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        progressDialog=new ProgressDialog(this);
        toolbar.setTitle("Friends");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView23);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter3=new RecyclerAdapter3(arrayList);
        recyclerView.setAdapter(recyclerAdapter3);
        if(isNe()==false) {
            recycler1();
        }
        else{
            user=FirebaseAuth.getInstance().getCurrentUser();
            recycler();
        }
    }
    public void recycler1() {
        progressDialog.setMessage("Loading! Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        DBAdapter db = new DBAdapter(this);
        db.open();
        c = db.getAllContacts();
        if (c.getCount() > 0) {
            c.moveToPosition(0);
            do {
                if (!c.getString(3).equals("0")) {
                    final String username1, about1, key1, uid1;
                    final byte[] byte1;
                    final String a;
                    final Bitmap bitmap1;
                    username1 = c.getString(3);
                    if (c.getBlob(5) != null) {
                        byte1 = c.getBlob(5);
                        bitmap1 = getImage(byte1);
                        a = "1";
                    } else {
                        bitmap1 = null;
                        a = "0";
                    }
                    uid1 = c.getString(1);
                    if (!c.getString(13).equals("0")) {
                        about1 = c.getString(13);
                        key1 = "About";
                    } else if (!c.getString(7).equals("0")) {
                        about1 = c.getString(7);
                        key1 = "College";

                    } else if (!c.getString(8).equals("0")) {
                        about1 = c.getString(8);
                        key1 = "School";

                    } else if (!c.getString(11).equals("0")) {
                        about1 = c.getString(11);
                        key1 = "Home";

                    } else if (!c.getString(12).equals("0")) {
                        about1 = c.getString(12);
                        key1 = "Current";

                    } else {
                        about1 = "0";
                        key1 = "0";
                    }
                    RecyclerData3 mLog = new RecyclerData3();
                    mLog.setUid(uid1);
                    mLog.setInfo(about1);
                    mLog.setK(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mLog.setUsername(username1);
                    mLog.setkey(key1);
                    arrayList.add(mLog);
                    recyclerAdapter3.notifyData(arrayList);
                    if (!c.isLast()) {
                        c.moveToNext();

                    }
                }
            }
            while (!c.isLast());
        }
        progressDialog.dismiss();
    }

    public boolean isNe(){
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }
        catch(NullPointerException e){
            return false;
        }
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    private void recycler() {
        progressDialog.setMessage("Loading! Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Friends");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String k = dataSnapshot.getKey();
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference1.child(k).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String l, key1;
                        final String uid, about, username;
                        username = dataSnapshot.child("Username").getValue().toString();
                        uid = dataSnapshot.child("Uid").getValue().toString();

                        if (dataSnapshot.hasChild("About")) {
                            about = dataSnapshot.child("About").getValue().toString();
                            key1 = "About";
                        } else if (dataSnapshot.hasChild("College")) {
                            about = dataSnapshot.child("College").getValue().toString();
                            key1 = "College";
                        } else if (dataSnapshot.hasChild("School")) {
                            about = dataSnapshot.child("School").getValue().toString();
                            key1 = "School";
                        } else if (dataSnapshot.hasChild("Current")) {
                            about = dataSnapshot.child("Current").getValue().toString();
                            key1 = "Current";
                        } else if (dataSnapshot.hasChild("Home")) {
                            about = dataSnapshot.child("Home").getValue().toString();
                            key1 = "Home";
                        } else {
                            about = "0";
                            key1 = "0";
                        }
                        RecyclerData3 mLog = new RecyclerData3();
                        mLog.setUid(uid);
                        mLog.setInfo(about);
                        mLog.setK(user.getUid());
                        mLog.setUsername(username);
                        mLog.setkey(key1);
                        arrayList.add(mLog);
                        recyclerAdapter3.notifyData(arrayList);
                        progressDialog.dismiss();
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
                final String uid=dataSnapshot.getKey();
                int i;
                for(i=0;i<arrayList.size();i++){
                    if(arrayList.get(i).getUid().equals(uid)){
                        arrayList.remove(i);
                        recyclerAdapter3.notifyData(arrayList);
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friendstoolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search1);
        searchView = (android.support.v7.widget.SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        Intent data=new Intent(Friends.this,MainPage.class);
        startActivity(data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String t = s.toLowerCase();
        ArrayList<RecyclerData3> newList = new ArrayList<>();
        for(RecyclerData3 n :arrayList)
        {
            if(n.getUsername().toLowerCase().contains(t)) {
                newList.add(n);
            }
            else if(n.getInfo().toLowerCase().contains(t)) {
                newList.add(n);
            }
        }
        recyclerAdapter3.UpdateList(newList);
        return true;
    }
    public void onClick4(View v){


    }
}