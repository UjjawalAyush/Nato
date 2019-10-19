package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {
    EditText name1,startingdate1,endingstart1,description1;
    Uri uri;
    FirebaseAuth firebaseAuth;
    TextInputLayout name,endingdate,startingdate;
    FirebaseUser user;
    ProgressDialog progressDialog;
    int x=1;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1,arrayList2;
    StorageReference storageReference;
    String a,b,c,d,e,f;
    String m;
    int g,h,i,j,k;
    Toolbar toolbar;
    Spinner spinner1,spinner2,spinner3;
    ImageView circularImageView;
    RecyclerAdapter91 mAdapter1;
    ImageButton imageButton;
    String trip;
    RecyclerAdapter15 mAdapter;
    ArrayList<RecyclerData15> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        Intent data=getIntent();
        trip=data.getStringExtra("trip");
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1234);
        toolbar.setTitle("Add Schedule");
        progressDialog=new ProgressDialog(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public boolean isNe() {
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent data=new Intent(Schedule.this,Trip.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
    public void onClick(View v){
        Toast.makeText(this,"Schedule Successfully Added",Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}
