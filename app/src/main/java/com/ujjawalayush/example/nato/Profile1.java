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
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Profile1 extends AppCompatActivity {
    ArrayList<RecyclerData> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    Toolbar toolbar;
    Uri uri;
    RecyclerAdapter recyclerAdapter;
    StorageReference storageReference;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth firebaseAuth;
    int x;
    byte[] byteVB;
    FirebaseUser user;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    String row;
    ImageView imageView,imageView1;
    Bitmap bitmap;
    int f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile1);
        toolbar=(Toolbar)findViewById(R.id.toolbar4);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerAdapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);
        user=FirebaseAuth.getInstance().getCurrentUser();
        imageView=(ImageView)findViewById(R.id.imageView3);
        progressBar=(ProgressBar)findViewById(R.id.progress1);
        imageView1=(ImageView)findViewById(R.id.imageView38);
        progressDialog=new ProgressDialog(this);
        progressBar.setVisibility(View.INVISIBLE);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("PhotoUrl")) {
                    Picasso.get().load(Uri.parse(dataSnapshot.child("PhotoUrl").getValue().toString())).centerCrop().fit().into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        user=firebaseAuth.getCurrentUser();
        RecyclerViewSelection();
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public void onClick1(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, x);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==x&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            progressDialog.setMessage("Changing Profile Pic...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            uri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                if(isNe()==false) {
                    Toast.makeText(Profile1.this, "Sorry! Network problem..", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                    storageReference=FirebaseStorage.getInstance().getReference().child("Images").child(Long.toString(System.currentTimeMillis()) + "." + getFileExtension(uri));
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri1) {
                                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                databaseReference.child("PhotoUrl").setValue(uri1.toString());
                                if(user.getPhotoUrl()!=null) {
                                    StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(user.getPhotoUrl().toString());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Profile1.this,"Profile Pic. successfully changed",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                UserProfileChangeRequest profileUpdates1 = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(uri1).build();
                                user.updateProfile(profileUpdates1);
                                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                databaseReference1.child("PhotoUrl").setValue(uri1.toString());
                                Toast.makeText(Profile1.this,"Profile Pic. successfully changed",Toast.LENGTH_LONG).show();
                                Picasso.get().load(uri).centerCrop().fit().into(imageView);
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
            } catch (IOException e1) {
                e1.printStackTrace();
                progressDialog.dismiss();
            }
        }
    }
    public String getFileExtension(Uri uri1){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
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
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public void RecyclerViewSelection() {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RecyclerData mLog=new RecyclerData();
                RecyclerData mLog1=new RecyclerData();
                RecyclerData mLog2=new RecyclerData();
                RecyclerData mLog3=new RecyclerData();
                RecyclerData mLog4=new RecyclerData();
                RecyclerData mLog5=new RecyclerData();
                RecyclerData mLog6=new RecyclerData();
                RecyclerData mLog7=new RecyclerData();
                RecyclerData mLog8=new RecyclerData();
                RecyclerData mLog9=new RecyclerData();
                mLog1.setDisplay("Mobile No.:");
                mLog2.setDisplay("Date of Birth:");
                mLog9.setDisplay("About me:");
                mLog3.setDisplay("Home Town:");
                mLog4.setDisplay("Current City:");
                mLog5.setDisplay("College:");
                mLog6.setDisplay("Year of Graduation:");
                mLog7.setDisplay("School:");
                mLog8.setDisplay("Year of passing:");
                mLog1.setValue(dataSnapshot.child("Number").getValue().toString());
                if(dataSnapshot.hasChild("Dob")){
                    mLog2.setValue(dataSnapshot.child("Dob").getValue().toString());
                }
                else{
                    mLog2.setValue(null);

                }
                if(dataSnapshot.hasChild("About")){
                    mLog9.setValue(dataSnapshot.child("About").getValue().toString());
                }
                else{
                    mLog9.setValue(null);

                }
                if(dataSnapshot.hasChild("Home")){
                    mLog3.setValue(dataSnapshot.child("Home").getValue().toString());
                }
                else{
                    mLog3.setValue(null);

                }
                if(dataSnapshot.hasChild("Current")){
                    mLog4.setValue(dataSnapshot.child("Current").getValue().toString());
                }
                else{
                    mLog4.setValue(null);

                }
                if(dataSnapshot.hasChild("College")){
                    mLog5.setValue(dataSnapshot.child("College").getValue().toString());
                }
                else{
                    mLog5.setValue(null);

                }
                if(dataSnapshot.hasChild("CollegeP")){
                    mLog6.setValue(dataSnapshot.child("CollegeP").getValue().toString());
                }
                else{
                    mLog6.setValue(null);

                }
                if(dataSnapshot.hasChild("School")){
                    mLog7.setValue(dataSnapshot.child("School").getValue().toString());
                }
                else{
                    mLog7.setValue(null);

                }
                if(dataSnapshot.hasChild("SchoolP")){
                    mLog8.setValue(dataSnapshot.child("SchoolP").getValue().toString());
                }
                else{
                    mLog8.setValue(null);

                }
                arrayList.add(mLog1);
                arrayList.add(mLog2);
                arrayList.add(mLog9);
                arrayList.add(mLog3);
                arrayList.add(mLog4);
                arrayList.add(mLog5);
                arrayList.add(mLog6);
                arrayList.add(mLog7);
                arrayList.add(mLog8);
                recyclerAdapter.notifyData(arrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
        Intent data=new Intent(Profile1.this,MainPage.class);
        startActivity(data);
    }
}
