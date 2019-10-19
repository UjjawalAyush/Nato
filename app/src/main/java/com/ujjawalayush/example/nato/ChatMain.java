package com.ujjawalayush.example.nato;

import  android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatMain extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    Toolbar toolbar;
    FirebaseUser user;
    ProgressDialog progressDialog;
    RecyclerAdapter5 mAdapter;
    TextView textView;
    ArrayList<RecyclerData5> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.chatmain1);
            recyclerView=(RecyclerView)findViewById(R.id.recyclerView199);
            toolbar=(Toolbar)findViewById(R.id.toolbar199);
            toolbar.setTitle("Chats");
            progressDialog=new ProgressDialog(this);

            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
            setSupportActionBar(toolbar);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter=new RecyclerAdapter5(myList);
            recyclerView.setAdapter(mAdapter);
            user=FirebaseAuth.getInstance().getCurrentUser();
        if(isNe()) {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loading! Please Wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            DatabaseReference databaseReference12 = FirebaseDatabase.getInstance().getReference().child("Chat").child(user.getUid());
            databaseReference12.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final String k = dataSnapshot1.getKey();
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(k);
                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final String username = dataSnapshot.child("Username").getValue().toString();
                                    DatabaseReference databaseReference23 = FirebaseDatabase.getInstance().getReference().child("Chat").child(user.getUid()).child(k);
                                    databaseReference23.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            long i = dataSnapshot.getChildrenCount();
                                            long h = 0;
                                            if (i > 0) {
                                                DBAdapter db = new DBAdapter(ChatMain.this);
                                                db.open();
                                                Cursor c = db.getAllContacts2();
                                                final long uid;
                                                String uid1 = "-1";
                                                c.moveToFirst();
                                                if (c.getCount() > 0) {
                                                    while (!c.isAfterLast()) {
                                                        if (c.getString(4).equals(k)) {
                                                            uid1 = c.getString(0);
                                                            break;
                                                        }
                                                        c.moveToNext();
                                                    }
                                                }
                                                uid = Long.parseLong(uid1);
                                                for (DataSnapshot dataSnapshot11 : dataSnapshot.getChildren()) {
                                                    h++;
                                                    final String mess, time;
                                                    mess = dataSnapshot11.child("Message").getValue().toString();
                                                    final String status = dataSnapshot11.child("Status").getValue().toString();
                                                    time = dataSnapshot11.child("Time").getValue().toString();
                                                    if(status.equals("Sent")){
                                                        long id = db.insertContact3(mess, time,username,k,"1");

                                                    }
                                                    else{
                                                        long id = db.insertContact3(mess, time,username,k,"0");

                                                    }
                                                    if(uid!=-1) {
                                                        db.updateContact2(uid, mess, time, username, k,Long.toString(i));
                                                    }
                                                    else{
                                                        long id1 = db.insertContact2(mess, time,username,k,Long.toString(i));

                                                    }
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllContacts2();
        c.moveToFirst();
        if(c.getCount()>0) {
            while (!c.isAfterLast()) {
                final String uid = c.getString(4);
                final String username = c.getString(3);
                final String mess=c.getString(1);
                final String time=c.getString(2);
                final String message;
                if (mess.length() > 20 && !mess.contains("\n")) {
                    message = mess.substring(0, 20) + "...";
                } else if (mess.length() > 20 && mess.contains("\n")) {
                    int j = mess.indexOf("\n");
                    if (j < 20) {
                        message = mess.substring(0, j) + "...";
                    } else {
                        message = mess.substring(0, 20) + "...";

                    }

                } else if (mess.contains("\n")) {
                    int j = mess.indexOf("\n");
                    message = mess.substring(0, j) + "...";
                } else {
                    message = mess;
                }
                RecyclerData5 mLog = new RecyclerData5();
                mLog.setUsername(username);
                mLog.setUid(uid);
                mLog.setInfo(message);
                mLog.setDate(time);
                myList.add(mLog);
                mAdapter.notifyData(myList);
                c.moveToNext();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addfriendstoolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search2);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String t = s.toLowerCase();
        ArrayList<RecyclerData5> newList = new ArrayList<>();
        for(RecyclerData5 n : myList)
        {
            if(n.getUsername().toLowerCase().contains(t)) {
                newList.add(n);
            }
        }
        mAdapter.UpdateList(newList);
        return true;
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
    @Override
    public void onBackPressed() {
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        super.onBackPressed();
        Intent data=new Intent(ChatMain.this,MainPage.class);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat").child(user.getUid());
        databaseReference.removeValue();
        startActivity(data);
    }
}
