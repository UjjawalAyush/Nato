package com.ujjawalayush.example.nato;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
public class Notifications extends AppCompatActivity{
    ProgressDialog progressDialog;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<RecyclerData11> arrayList=new ArrayList<>();
    String username;
    FirebaseUser user;
    Cursor c;
    byte[] byteBitmap;
    DBAdapter db;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter11 mAdapter;
    SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        progressDialog=new ProgressDialog(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar21);
        toolbar.setTitle("Notifications");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView21);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter11(arrayList);
        recyclerView.setAdapter(mAdapter);
        progressDialog.setMessage("Loading! Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllNotifications();
        c.moveToFirst();
        if(c.getCount()>0){
            while(!c.isAfterLast()){
                RecyclerData11 mLog=new RecyclerData11();
                mLog.setUid(c.getString(1));
                mLog.setMessage(c.getString(4));
                mLog.setTrip(c.getString(2));
                mLog.setUsername(c.getString(3));
                mLog.setDate(c.getString(5));
                arrayList.add(mLog);
                mAdapter.notifyData(arrayList);
                c.moveToNext();
            }
        }
        recyclerView.scrollToPosition(arrayList.size() - 1);
        progressDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
            Intent data=new Intent(Notifications.this,MainPage.class);
            startActivity(data);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}