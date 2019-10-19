package com.ujjawalayush.example.nato;
import android.app.ProgressDialog;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ArrayList<String> arrayList1,arrayList2;
    StorageReference storageReference;
    String a,b,c,d,e,f;
    String m;
    int g,h,i,j,k;
    Toolbar toolbar;
    Spinner spinner1,spinner2,spinner3;
    ImageView circularImageView;
    ImageButton imageButton;
    TextInputLayout textInputLayout,inputLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNe()==false){
            setContentView(R.layout.nointernet);
            toolbar=(Toolbar)findViewById(R.id.toolbar5);
            toolbar.setTitle("New Trip");
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
        else{
            setContentView(R.layout.add);
            progressDialog=new ProgressDialog(this);
            arrayList=new ArrayList<>();
            arrayList1=new ArrayList<>();
            arrayList2=new ArrayList<>();
            description=(EditText)findViewById(R.id.textInput3e);
            date=(EditText)findViewById(R.id.textInput2e);
            name=(EditText)findViewById(R.id.textInput1e);
            inputLayout=(TextInputLayout)findViewById(R.id.textInput1);
            textInputLayout=(TextInputLayout)findViewById(R.id.textInput2);
            spinner1=(Spinner)findViewById(R.id.spinner);
            spinner2=(Spinner)findViewById(R.id.spinner1);
            circularImageView=(ImageView)findViewById(R.id.circularImageView1);
            toolbar=(Toolbar)findViewById(R.id.toolbar1);
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
            arrayList.add("Errand");
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
            arrayList2.add("Anyone can join!");
            arrayList2.add("Invite only");
            arrayList2.add("Closed");
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList2);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3=(Spinner)findViewById(R.id.spinner2);
            spinner3.setAdapter(arrayAdapter2);
            spinner3.setSelection(0);
            m=spinner3.getSelectedItem().toString();
            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    m=parent.getItemAtPosition(position).toString();
                }
                @Override
                public void onNothingSelected(AdapterView <?> parent) {
                }
            });
        }

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
            TripQuery=FirebaseDatabase.getInstance().getReference().child("Trips").orderByChild("name").equalTo(name1);
        }
        else{
            TripQuery=FirebaseDatabase.getInstance().getReference().child("Errands").orderByChild("name").equalTo(name1);
        }
        TripQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    inputLayout.setError("Choose another name");
                    inputLayout.requestFocus();
                    progressDialog.dismiss();
                    return;
                }
                else{
                    if(uri!=null) {
                        storageReference = FirebaseStorage.getInstance().getReference().child("Trip").child(Long.toString(System.currentTimeMillis()) + "." + getFileExtension(uri));
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri1) {
                                        a=spinner1.getSelectedItem().toString();
                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
                                            databaseReference.child(name1).setValue("Yes");
                                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Trips").child(name1);
                                            databaseReference1.child("members").child(user.getUid()).setValue(user.getDisplayName());
                                            databaseReference1.child("name").setValue(name1);
                                            b=spinner2.getSelectedItem().toString();
                                            m=spinner3.getSelectedItem().toString();
                                            databaseReference1.child("Details").child("Status").setValue(m);
                                            databaseReference1.child("Details").child("Expected").setValue(b);
                                            databaseReference1.child("Details").child("members").setValue("1");
                                            databaseReference1.child("Details").child("description").setValue(description1);
                                            databaseReference1.child("Details").child("date").setValue(date1);
                                            databaseReference1.child("Details").child("type").setValue(a);
                                            databaseReference1.child("Details").child("Add").setValue(user.getUid());
                                            databaseReference1.child("Details").child("photo").setValue(uri1.toString());
                                            DBAdapter db=new DBAdapter(Add.this);
                                            db.open();
                                            db.insertTrip(a,date1,name1,description1,m,b,"1");
                                            db.close();
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
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
                            databaseReference.child(name1).setValue("Yes");
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Trips").child(name1);
                            databaseReference1.child("members").child(user.getUid()).setValue(user.getDisplayName());
                            databaseReference1.child("name").setValue(name1);
                            b=spinner2.getSelectedItem().toString();
                            databaseReference1.child("Details").child("Add").setValue(user.getUid());
                            m=spinner3.getSelectedItem().toString();
                            databaseReference1.child("Details").child("members").setValue("1");
                            databaseReference1.child("Details").child("type").setValue(a);

                        databaseReference1.child("Details").child("Status").setValue(m);
                            databaseReference1.child("Details").child("Expected").setValue(b);
                            databaseReference1.child("Details").child("description").setValue(description1);
                            databaseReference1.child("Details").child("date").setValue(date1);
                            DBAdapter db=new DBAdapter(Add.this);
                            db.open();
                            db.insertTrip(a,date1,name1,description1,m,b,"1");
                            db.close();
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
            Picasso.get().load(uri).centerCrop().fit().into(circularImageView);
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
        Intent data=new Intent(Add.this,MainPage.class);
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
