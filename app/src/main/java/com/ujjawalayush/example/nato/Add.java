package com.ujjawalayush.example.nato;
import android.app.ProgressDialog;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Add extends AppCompatActivity {
    EditText description,date,name;
    Uri uri;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    int x=1;
    String description1,date1,name1;
    byte[] photoByte;
    Query TripQuery;
    Bitmap bitmap;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList1;

    String a,b,c,d,e,f;
    int g,h,i,j,k;
    Spinner spinner1,spinner2;
    CircularImageView circularImageView;
    ImageButton imageButton;
    TextInputLayout textInputLayout,inputLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        progressDialog=new ProgressDialog(this);
        arrayList=new ArrayList<>();
        arrayList1=new ArrayList<>();
        description=(EditText)findViewById(R.id.textInput3e);
        date=(EditText)findViewById(R.id.textInput2e);
        name=(EditText)findViewById(R.id.textInput1e);
        inputLayout=(TextInputLayout)findViewById(R.id.textInput1);
        textInputLayout=(TextInputLayout)findViewById(R.id.textInput2);
        spinner1=(Spinner)findViewById(R.id.spinner);
        spinner2=(Spinner)findViewById(R.id.spinner1);
        circularImageView=(CircularImageView)findViewById(R.id.circularImageView1);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1);
        toolbar.setTitle("New Trip");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        arrayList.add("Trip");
        arrayList.add("Errands");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setSelection(0);
        a=spinner1.getSelectedItem().toString();
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                description.setHint("");
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        arrayList1.add("Expected Date");
        arrayList1.add("Expected Duration");
        arrayList1.add("Finalized Date");
        arrayList1.add("Finalized Duration");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter1);
        spinner2.setSelection(0);
        b=spinner2.getSelectedItem().toString();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onClick(View v){
        name1=name.getText().toString().trim();
        if(name1.equals("")){
            inputLayout.setError("Choose a trip Name");
            inputLayout.requestFocus();
            return;
        }
        date1=date.getText().toString().trim();
        if(date1.equals("")){
            textInputLayout.setError("Write expected date or duration");
            textInputLayout.requestFocus();
            return;
        }
        description1=description.getText().toString().trim();
        if(description1.equals("")){
            description.setError("Write a brief description");
            description.requestFocus();
            return;
        }
        progressDialog.setMessage("Save Trip Info...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        a=spinner1.getSelectedItem().toString();
        if(a.equals("Trip")){
            TripQuery=FirebaseDatabase.getInstance().getReference().child("Trips").equalTo(name1);
        }
        else{
            TripQuery=FirebaseDatabase.getInstance().getReference().child("Errands").equalTo(name1);
        }
        TripQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    textInputLayout.setError("Choose another name");
                    textInputLayout.requestFocus();
                    progressDialog.dismiss();
                    return;
                }
                else{
                    if(uri!=null){
                        final StorageReference storageReference=FirebaseStorage.getInstance().getReference().child(Long.toString(System.currentTimeMillis()) + "." + getFileExtension(uri));
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri1) {
                                        byte[] byteBitmap=getBytes(bitmap);
                                        d=new String(byteBitmap);
                                            a=spinner1.getSelectedItem().toString();
                                            if(a.equals("Trip")) {
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
                                                databaseReference.child(name1).setValue("Yes");
                                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Trips").child(name1);
                                                databaseReference1.child("members").child(user.getUid()).child("Yes");
                                                databaseReference1.child("Details").child("name").setValue(name1);
                                                b=spinner2.getSelectedItem().toString();
                                                databaseReference1.child("Details").child("Expected").setValue(b);
                                                databaseReference1.child("Details").child("description").setValue(description1);
                                                databaseReference1.child("Details").child("date").setValue(date1);
                                                databaseReference1.child("Details").child("photo").setValue(d);
                                            }
                                            else{
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Errands");
                                                databaseReference.child(name1).setValue("Yes");
                                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Errands").child(name1);
                                                databaseReference1.child("members").child(user.getUid()).child("Yes");
                                                databaseReference1.child("Details").child("name").setValue(name1);
                                                b=spinner2.getSelectedItem().toString();
                                                databaseReference1.child("Details").child("Expected").setValue(b);
                                                databaseReference1.child("Details").child("description").setValue(description1);
                                                databaseReference1.child("Details").child("date").setValue(date1);
                                                databaseReference1.child("Details").child("photo").setValue(d);
                                            }

                                        progressDialog.dismiss();
                                        Toast.makeText(Add.this,"Trip successfully created",Toast.LENGTH_LONG).show();
                                        Intent data=new Intent(Add.this,MainPage.class);
                                        startActivity(data);
                                    }
                                });
                            }
                        });
                    }
                    else{
                        a=spinner1.getSelectedItem().toString();
                        if(a.equals("Trip")) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
                            databaseReference.child(name1).setValue("Yes");
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Trips").child(name1);
                            databaseReference1.child("members").child(user.getUid()).child("Yes");
                            databaseReference1.child("Details").child("name").setValue(name1);
                            b=spinner2.getSelectedItem().toString();
                            databaseReference1.child("Details").child("Expected").setValue(b);
                            databaseReference1.child("Details").child("description").setValue(description1);
                            databaseReference1.child("Details").child("date").setValue(date1);
                        }
                        else{
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Errands");
                            databaseReference.child(name1).setValue("Yes");
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Errands").child(name1);
                            databaseReference1.child("members").child(user.getUid()).child("Yes");
                            databaseReference1.child("Details").child("name").setValue(name1);
                            b=spinner2.getSelectedItem().toString();
                            databaseReference1.child("Details").child("Expected").setValue(b);
                            databaseReference1.child("Details").child("description").setValue(description1);
                            databaseReference1.child("Details").child("date").setValue(date1);
                        }
                        progressDialog.dismiss();
                        Toast.makeText(Add.this,"Trip successfully created",Toast.LENGTH_LONG).show();
                        Intent data=new Intent(Add.this,MainPage.class);
                        startActivity(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
            Picasso.get().load(uri).into(circularImageView);
        }
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

    }
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
