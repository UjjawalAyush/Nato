package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Invite extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView,recyclerView1;
    FirebaseUser user;
    Toolbar toolbar;
    String tag;
    SearchView searchView;
    RecyclerAdapter12 mAdapter;
    String trip;
    ArrayList<String> hiList=new ArrayList<>();
    ArrayList<String> biList=new ArrayList<>();

    RecyclerAdapter13 gAdapter;
    ArrayList<RecyclerData12> myList=new ArrayList<>();
    ArrayList<RecyclerData12> newList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNe()) {
            setContentView(R.layout.invite);
            user = FirebaseAuth.getInstance().getCurrentUser();
            toolbar = (Toolbar) findViewById(R.id.toolbar3131);
            toolbar.setTitle("Invite People");
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
            setSupportActionBar(toolbar);
            Intent data = getIntent();
            trip = data.getStringExtra("trip");
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView341);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            gAdapter = new RecyclerAdapter13(newList);
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c = db.getAllX();
            c.moveToFirst();
            while (!c.isAfterLast()) {
                if (trip.equals(c.getString(2))) {
                    biList.add(c.getString(3));
                }
                c.moveToNext();
            }
            recyclerView.setAdapter(gAdapter);
            recyclerView.setVisibility(View.INVISIBLE);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView351);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
            recyclerView1.setLayoutManager(staggeredGridLayoutManager);
            mAdapter = new RecyclerAdapter12(myList);
            recyclerView1.setAdapter(mAdapter);
            String member = "";
            for (int q = 0; q < biList.size(); q++) {
                if (q < biList.size() - 2) {
                    member = member + biList.get(q) + ",";
                } else if (q == biList.size() - 2) {
                    member = member + biList.get(q) + " and ";
                } else {
                    member = member + biList.get(q);
                }
            }
            if (!member.equals("")) {
                Toast.makeText(this, member + " have already been invited", Toast.LENGTH_LONG).show();
            }
            recycler();
        }
        else{
            setContentView(R.layout.nointernet);
            toolbar=(Toolbar)findViewById(R.id.toolbar5);
            Intent data = getIntent();
            trip = data.getStringExtra("trip");
            toolbar.setTitle(trip);
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c = db.getAllX();
            c.moveToFirst();
            while (!c.isAfterLast()) {
                if (trip.equals(c.getString(2))) {
                    biList.add(c.getString(3));
                }
                c.moveToNext();
            }
            String member = "";
            for (int q = 0; q < biList.size(); q++) {
                if (q < biList.size() - 2) {
                    member = member + biList.get(q) + ",";
                } else if (q == biList.size() - 2) {
                    member = member + biList.get(q) + " and ";
                } else {
                    member = member + biList.get(q);
                }
            }
            if (!member.equals("")) {
                Toast.makeText(this, member + " have already been invited", Toast.LENGTH_LONG).show();
            }
        }
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
    private void recycler() {
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllNotification();
        if(c.getCount()>0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(c.getString(2).equals(trip)&&c.getString(4).equals("Invitation")) {
                    RecyclerData12 mLog = new RecyclerData12();
                    mLog.setUsername(c.getString(3));
                    mLog.setUid(c.getString(1));
                    mLog.setTrip(c.getString(2));
                    mLog.setMessage(c.getString(4));
                    myList.add(mLog);
                    mAdapter.notifyData(myList);
                }
                c.moveToNext();
            }
        }
        db.close();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    final String k = dataSnapshot1.getKey();
                    if (!k.equals(user.getUid())) {
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(k);
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String username = dataSnapshot.child("Username").getValue().toString();
                                RecyclerData12 mLog = new RecyclerData12();
                                mLog.setUsername(username);
                                mLog.setUid(dataSnapshot.child("Uid").getValue().toString());
                                mLog.setTrip(trip);
                                mLog.setMessage("Invitation");
                                newList.add(mLog);
                                gAdapter.notifyData(newList);
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
    }
    public void onClick15(View v){
        DBAdapter db=new DBAdapter(Invite.this);
        db.open();
        Cursor c=db.getAllNotification();
        c.moveToFirst();
        if(c.getCount()>0){
            while(!c.isAfterLast()){
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Invitations").child(trip).child(c.getString(1));
                databaseReference.child(user.getUid()).child("Username").setValue(user.getDisplayName());
                databaseReference.child(user.getUid()).child("Status").setValue("Invited");
                databaseReference.child(user.getUid()).child("Trip").setValue(trip);


                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Invitations").child(trip).child(user.getUid());
                databaseReference1.child(c.getString(1)).child("Username").setValue(c.getString(3));
                databaseReference1.child(c.getString(1)).child("Status").setValue("Invitation");
                databaseReference1.child(c.getString(1)).child("Trip").setValue(trip);

                if(!biList.contains(c.getString(3))) {
                    db.insertX(c.getString(1), c.getString(2), c.getString(3));
                }
                c.moveToNext();
            }
        }
        db.deleteAllNotification();
        Toast.makeText(this,"Invitation Sent",Toast.LENGTH_SHORT).show();
        Intent data=new Intent(Invite.this,Trip.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addfriendstoolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search2);
        searchView = (android.support.v7.widget.SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener( this);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DBAdapter db=new DBAdapter(this);
        db.open();
        db.deleteAllNotification();
        db.close();
        Intent data=new Intent(Invite.this,Trip.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
    public void restart(){
        this.recreate();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String t=s.toLowerCase();
        ArrayList<RecyclerData12> d=new ArrayList<>();
        recyclerView.setVisibility(View.VISIBLE);

        if (!myList.contains(t)) {
            for(RecyclerData12 x:newList){
                if(x.getUsername().toLowerCase().contains(t)){
                    d.add(x);
                }
            }
        }
        gAdapter.UpdateList(d);
        return true;
    }
}
