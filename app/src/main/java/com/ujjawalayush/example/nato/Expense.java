package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Expense extends AppCompatActivity {
    EditText description,date,category,money;
    Uri uri;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    int x=1;
    RecyclerView recyclerView,recyclerView1;
    String description1,date1,category1,money1;
    byte[] photoByte;
    Query TripQuery;
    Bitmap bitmap;
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

    TextInputLayout category2,money2,date2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        Intent data=getIntent();
        recyclerView=(RecyclerView)findViewById(R.id.rr);
        mAdapter=new RecyclerAdapter15(myList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1234);
        trip=data.getStringExtra("trip");
        circularImageView=(ImageView)findViewById(R.id.circularImageView1234);
        date=(EditText)findViewById(R.id.textInput1235e);
        date2=(TextInputLayout)findViewById(R.id.textInput1235);
        description=(EditText)findViewById(R.id.textInput31234e);
        money=(EditText)findViewById(R.id.textInput12356e);
        money2=(TextInputLayout)findViewById(R.id.textInput12356);
        category=(EditText)findViewById(R.id.textInput1234e);
        category2=(TextInputLayout)findViewById(R.id.textInput1234);
        toolbar.setTitle("Add Expense");
        recyclerView.setAdapter(mAdapter);
        progressDialog=new ProgressDialog(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("members");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    RecyclerData15 mLog=new RecyclerData15();
                    mLog.setUsername(dataSnapshot1.getValue().toString());
                    mLog.setUid(dataSnapshot1.getKey());
                    myList.add(mLog);
                    mAdapter.notifyData(myList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
    public String getFileExtension(Uri uri1){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
    }
    @Override
    public void onBackPressed() {
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        super.onBackPressed();
        Intent data=new Intent(Expense.this,Trip.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public void onClick1(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, x);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==x&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Picasso.get().load(uri).centerCrop().fit().into(circularImageView);
        }
    }
    public void onClick(View v){
        date1=date.getText().toString().trim();
        description1=description.getText().toString().trim();
        Toast.makeText(this,"Expense Successfully Added",Toast.LENGTH_LONG).show();
        onBackPressed();
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
}
