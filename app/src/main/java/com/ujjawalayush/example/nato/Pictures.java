package com.ujjawalayush.example.nato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Pictures extends AppCompatActivity {
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    FirebaseUser user;
    Toolbar toolbar;
    String tag;
    EditText editText;
    RecyclerAdapter6 mAdapter;
    ImageView imageView;
    StorageReference storageReference;
    Uri uri;
    ArrayList<Uri> myList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNe()==false){
            setContentView(R.layout.nointernet);
            progressDialog=new ProgressDialog(this);
            toolbar=(Toolbar)findViewById(R.id.toolbar31);
            toolbar.setTitle("Post");
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        else{

            setContentView(R.layout.pictures);
            progressDialog=new ProgressDialog(this);
            toolbar=(Toolbar)findViewById(R.id.toolbar31);
            toolbar.setTitle("Post");
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            recyclerView=(RecyclerView)findViewById(R.id.horizontal);
            mAdapter=new RecyclerAdapter6(myList);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
            linearLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(mAdapter);
            imageView=(ImageView )findViewById(R.id.ima);
            editText=(EditText ) findViewById(R.id.textInput31e);
            TextView t=(TextView)findViewById(R.id.wq);

            imageView.setVisibility(View.INVISIBLE);
            user=FirebaseAuth.getInstance().getCurrentUser();
            DBAdapter db=new DBAdapter(this);
            db.open();
            Cursor d=db.getAllContacts1();
            d.moveToFirst();
            if(d.getCount()>0){
                while(!d.isAfterLast()){
                    myList.add(Uri.parse(d.getString(1)));
                    mAdapter.notifyData(myList,1);
                    d.moveToNext();
                }
            }
            Cursor c=db.getAllContacts();
            c.moveToFirst();
            if(c.getCount()>0){
                t.setText(Long.toString(c.getCount())+" People");

            }
            db.close();
        }
    }
    public void onClick1(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);
    }
    public void onClick(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"),1);
    }
    public void onClick6(View v) {
        final String time = Long.toString(System.currentTimeMillis());
        progressDialog.setMessage("Saving! Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        final String b = editText.getText().toString().trim();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(time);
        databaseReference.child("Details").child("EditText").setValue(b);
        databaseReference.child("Details").child("uid").setValue(user.getUid());
        databaseReference.child("Details").child("username").setValue(user.getDisplayName());
        databaseReference.child("Details").child("time").setValue(time);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Posts1").child(user.getUid()).child(time);
        databaseReference1.child("Details").child("EditText").setValue(b);
        databaseReference1.child("Details").child("time").setValue(time);
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllContacts();
        db.deleteAllContacts1();
        long g=db.insertContact1(time);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {
                final String d = databaseReference.child("tag").push().getKey();
                databaseReference.child("tag").child(d).child("username").setValue(c.getString(2));
                databaseReference.child("tag").child(d).child("uid").setValue(c.getString(1));
                databaseReference1.child("tag").child(d).child("username").setValue(c.getString(2));
                databaseReference1.child("tag").child(d).child("uid").setValue(c.getString(1));
                c.moveToNext();
            }
        }
        db.close();

        for(int a=0;a<myList.size();a++){
            final String f=Long.toString(System.currentTimeMillis())+"."+getFileExtension(myList.get(a));
            DBAdapter db1=new DBAdapter(this);
            db1.open();
            long i=db1.insertContact1(f);
            storageReference=FirebaseStorage.getInstance().getReference().child("Images1").child(time).child(f);
            storageReference.putFile(myList.get(a)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            link(uri);

                        }
                    });
                }
            });
        }

    }

    private void link(Uri uri) {
        Toast.makeText(Pictures.this,"Post successfully shared", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent data=new Intent(Pictures.this,MainPage.class);
        startActivity(data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onClick3(View v){
        Intent data=new Intent(Pictures.this,tag.class);
        startActivityForResult(data,3);
    }
    @Override
    public void onBackPressed() {
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        super.onBackPressed();
        Intent data=new Intent(Pictures.this,MainPage.class);
        startActivity(data);
    }
    public String getFileExtension(Uri uri1){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    imageView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    if(count==0){
                        Toast.makeText(this,"Long Click to select images",Toast.LENGTH_LONG).show();
                    }
                    for(int i = 0; i < count; i++) {
                        myList.add(data.getClipData().getItemAt(i).getUri());
                        mAdapter.notifyData(myList, 1);
                        DBAdapter db = new DBAdapter(this);
                        db.open();
                        long id = db.insertContact1(data.getClipData().getItemAt(i).getUri().toString());
                        db.close();
                    }
                }
                else{
                    Toast.makeText(this,"Long Click to select multiple images",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this,"Long Click to select multiple images",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 2){
            if(resultCode==RESULT_OK){
                uri=data.getData();
                recyclerView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                Picasso.get().load(uri).centerCrop().fit().into(imageView);
                mAdapter.notifyData(myList,2);
                myList.add(uri);
                DBAdapter db = new DBAdapter(this);
                db.open();
                db.deleteAllContacts1();
                long id = db.insertContact1(uri.toString());
                db.close();
                mAdapter.notifyData(myList,1);
            }

        }
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
