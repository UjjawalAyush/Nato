package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Trip1 extends AppCompatActivity {
    EditText description,date,name;
    Uri uri;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    int x=1;
    RecyclerView recyclerView,recyclerView1;
    String description1,date1,name1,trip;
    byte[] photoByte;
    Query TripQuery;
    Bitmap bitmap;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1,arrayList2;
    StorageReference storageReference;
    String a,b,c,d,e,f;
    TextView textView;
    String m;
    ImageView imageView;
    int g,h,i,j,k;
    RecyclerAdapter17 mAdapter1;
    ArrayList<RecyclerData17> myList1=new ArrayList<>();
    Toolbar toolbar;
    ArrayList<RecyclerData> myList=new ArrayList<>();
    RecyclerAdapter9 mAdapter;
    Spinner spinner1,spinner2,spinner3;
    ImageView circularImageView;
    ImageButton imageButton;
    TextInputLayout textInputLayout,inputLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip1);
        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        Intent data=getIntent();
        trip=data.getStringExtra("trip");
        toolbar.setTitle(trip);
        textView=(TextView)findViewById(R.id.textView);
        imageView=(ImageView)findViewById(R.id.imageView);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("photo")){
                    Picasso.get().load(dataSnapshot.child("photo").getValue().toString()).centerCrop().fit().into(imageView);
                }
                String text=dataSnapshot.child("Status").getValue().toString();
                if(text.equals("Anyone can join!")) {
                    textView.setText("Join Group");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.rr);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter9(myList);
        recyclerView.setAdapter(mAdapter);
        myList.clear();
        mAdapter.notifyData(myList);
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip);
        databaseReference1.child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    RecyclerData mLog=new RecyclerData();
                    mLog.setDisplay(dataSnapshot1.getKey());
                    mLog.setValue(dataSnapshot1.getValue().toString());
                    myList.add(mLog);
                    mAdapter.notifyData(myList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView1=(RecyclerView)findViewById(R.id.r);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        mAdapter1=new RecyclerAdapter17(myList1);
        recyclerView1.setAdapter(mAdapter1);
        myList1.clear();
        mAdapter1.notifyData(myList1);
        RecyclerData17 mLog1=new RecyclerData17();
        mLog1.setStartingDate("21-03-2000");
        mLog1.setEndingDate("21-03-2000");
        mLog1.setName("Dehradun");
        mLog1.setBy("Ujjawal");
        mLog1.setX("0");
        mLog1.setTrip(trip);
        mLog1.setTime(Long.toString(System.currentTimeMillis()));
        mLog1.setDescription("Birthday Party");
        myList1.add(mLog1);
        mAdapter1.notifyData(myList1);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onClick23(View v){

        Intent data=new Intent(Trip1.this,MainPage.class);
        startActivity(data);
    }
}
