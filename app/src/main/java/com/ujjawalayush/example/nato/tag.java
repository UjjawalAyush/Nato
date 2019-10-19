package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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

public class tag extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView,recyclerView1;
    FirebaseUser user;
    Toolbar toolbar;
    String tag;
    SearchView searchView;
    RecyclerAdapter7 mAdapter;
    RecyclerAdapter8 gAdapter;
    ArrayList<RecyclerData8> myList;
    ArrayList<RecyclerData8> newList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag);
        myList=new ArrayList<>();
        user=FirebaseAuth.getInstance().getCurrentUser();
        toolbar=(Toolbar)findViewById(R.id.toolbar313);
        toolbar.setTitle("Tag People");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView34);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        gAdapter=new RecyclerAdapter8(newList);
        recyclerView.setAdapter(gAdapter);
        recyclerView.setVisibility(View.INVISIBLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerView35);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(staggeredGridLayoutManager);
        mAdapter=new RecyclerAdapter7(myList);
        recyclerView1.setAdapter(mAdapter);
        recycler();

    }

    private void recycler() {
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllContacts();
        if(c.getCount()>0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                RecyclerData8 mLog = new RecyclerData8();
                mLog.setUsername(c.getString(2));
                mLog.setNew(c.getString(1));
                myList.add(mLog);
                mAdapter.notifyData(myList);
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
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(k);
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String username = dataSnapshot.child("Username").getValue().toString();
                                RecyclerData8 mLog = new RecyclerData8();
                                mLog.setUsername(username);
                                mLog.setNew(dataSnapshot.child("Uid").getValue().toString());
                                newList.add(mLog);
                                gAdapter.notifyData(newList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onClick15(View v){
        Toast.makeText(this,"Data saved",Toast.LENGTH_SHORT).show();
        Intent data=new Intent(tag.this,Pictures.class);
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
        db.deleteAllContacts();
        db.close();
        Intent data=new Intent(tag.this,Pictures.class);
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
        ArrayList<RecyclerData8> d=new ArrayList<>();
        recyclerView.setVisibility(View.VISIBLE);

        if (!myList.contains(t)) {
            for(RecyclerData8 x:newList){
                if(x.getUsername().toLowerCase().contains(t)){
                    d.add(x);
                }
            }
        }
        gAdapter.UpdateList(d);
        return true;
    }
}
