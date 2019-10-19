package com.ujjawalayush.example.nato;

import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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

public class Signup extends AppCompatActivity {
    private EditText editText2, editText3, editText4;
    private Button b2;
    ProgressDialog progressDialog;
    Cursor c;
    EditText username, number;
    String f;
    String Username, phone, a;
    Button button;
    Uri uri;
    TextView email1, password1, username1, number1;
    FirebaseAuth firebaseAuth;
    String axe;
    StorageReference storageReference;
    int x = 1;
    String email, password;
    String user_id, b;
    FirebaseUser user;
    DBAdapter db;
    CircularImageView circularImageView;
    DatabaseReference databaseReference;
    Query UsernameQuery, NumberQuery,EmailQuery;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        email1 = (TextView) findViewById(R.id.textView6);
        password1 = (TextView) findViewById(R.id.textView7);
        username1 = (TextView) findViewById(R.id.textView8);
        circularImageView = (CircularImageView) findViewById(R.id.circularImageView);
        number1 = (TextView) findViewById(R.id.textView9);
        email1.setVisibility(View.INVISIBLE);
        button = (Button) findViewById(R.id.button2);
        password1.setVisibility(View.INVISIBLE);
        username1.setVisibility(View.INVISIBLE);
        number1.setVisibility(View.INVISIBLE);
        editText2 = (EditText) findViewById(R.id.editText);
        editText3 = (EditText) findViewById(R.id.editText2);
        number = (EditText) findViewById(R.id.editText6);
        username = (EditText) findViewById(R.id.editText5);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email1.setVisibility(View.VISIBLE);
                password1.setVisibility(View.INVISIBLE);
                username1.setVisibility(View.INVISIBLE);
                number1.setVisibility(View.INVISIBLE);
                editText2.setHint("");
                editText3.setHint("Password");
                username.setHint("Username");
                number.setHint("Phone Number");
            }
        });
        editText3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email1.setVisibility(View.INVISIBLE);
                password1.setVisibility(View.VISIBLE);
                username1.setVisibility(View.INVISIBLE);
                number1.setVisibility(View.INVISIBLE);
                editText2.setHint("Email");
                editText3.setHint("");
                username.setHint("Username");
                number.setHint("Phone Number");
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email1.setVisibility(View.INVISIBLE);
                password1.setVisibility(View.INVISIBLE);
                username1.setVisibility(View.VISIBLE);
                number1.setVisibility(View.INVISIBLE);
                editText2.setHint("Email");
                editText3.setHint("Password");
                username.setHint("");
                number.setHint("Phone Number");
            }
        });
        number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email1.setVisibility(View.INVISIBLE);
                password1.setVisibility(View.INVISIBLE);
                username1.setVisibility(View.INVISIBLE);
                number1.setVisibility(View.VISIBLE);
                editText2.setHint("Email");
                editText3.setHint("Password");
                username.setHint("Username");
                number.setHint("");
            }
        });
    }

    public void onClick1(View v) {
        Intent data = new Intent(Signup.this, MainActivity.class);
        startActivity(data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, x);
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

    public void onClick2(View v) {
        email = editText2.getText().toString().trim();
        password = editText3.getText().toString().trim();
        a = number.getText().toString().trim();
        Username = username.getText().toString().trim();
        if (email.equals("")) {
            editText2.setError("Email not entered");
            editText2.requestFocus();
            return;

        }
        if (password.equals("")) {
            editText3.setError("Password not entered");
            editText3.requestFocus();
            return;

        }
        if (password.length() < 6) {
            editText3.setError("Minimum Password length required 6");
            editText3.requestFocus();
            return;
        }
        if (Username.equals("")) {
            username.setError("Please enter a valid Username");
            username.requestFocus();
            return;
        }
        else if(Username.contains(" ")){
            username.setError("Username can't contain spaces");
            username.requestFocus();
            return;
        }
        if (a.length() != 10) {
            number.setError("Please enter a valid mobile no.");
            number.requestFocus();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (isNe() == false) {
            Toast.makeText(Signup.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        EmailQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Email").equalTo(email);
        EmailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    editText2.setError("Choose another Email-Id");
                    editText2.requestFocus();
                    progressDialog.dismiss();
                } else {
                    UsernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Username").equalTo(Username);
                    UsernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                username.setError("Choose another Username");
                                username.requestFocus();
                                progressDialog.dismiss();
                            } else {
                                b = "+91" + a;
                                NumberQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Number").equalTo(b);
                                NumberQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getChildrenCount() > 0) {
                                            number.setError("Choose another mobile no.");
                                            number.requestFocus();
                                            progressDialog.dismiss();
                                        } else {
                                            Activity activity = Signup.this;
                                            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        user = firebaseAuth.getCurrentUser();
                                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    x = 2;
                                                                    if (x == 2) {
                                                                        if (uri != null) {
                                                                            storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(Long.toString(System.currentTimeMillis()) + "." + getFileExtension(uri));
                                                                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                        @Override
                                                                                        public void onSuccess(Uri uri1) {
                                                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                                                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                                                    .setDisplayName(Username).build();
                                                                                            user.updateProfile(profileUpdates);
                                                                                            UserProfileChangeRequest profileUpdates1 = new UserProfileChangeRequest.Builder()
                                                                                                    .setPhotoUri(uri1).build();
                                                                                            user.updateProfile(profileUpdates1);
                                                                                            databaseReference.child("Username").setValue(Username);
                                                                                            databaseReference.child("Email").setValue(email);
                                                                                            databaseReference.child("Number").setValue(b);
                                                                                            databaseReference.child("Uid").setValue(user.getUid());
                                                                                            databaseReference.child("PhotoUrl").setValue(uri1.toString());
                                                                                            progressDialog.dismiss();
                                                                                            Toast.makeText(Signup.this, "Email Verification successfully sent to your email id", Toast.LENGTH_LONG).show();
                                                                                            Intent data = new Intent(Signup.this, MainActivity.class);
                                                                                            startActivity(data);

                                                                                        }
                                                                                    });
                                                                                }
                                                                            });
                                                                        } else {
                                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                                                                            databaseReference.child("Username").setValue(Username);
                                                                            databaseReference.child("Number").setValue(b);
                                                                            databaseReference.child("Email").setValue(email);
                                                                            databaseReference.child("Uid").setValue(user.getUid());
                                                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                                    .setDisplayName(Username).build();
                                                                            user.updateProfile(profileUpdates);
                                                                            progressDialog.dismiss();
                                                                            Intent data = new Intent(Signup.this, MainActivity.class);
                                                                            startActivity(data);
                                                                        }
                                                                    } else if (task.isCanceled()) {
                                                                        Toast.makeText(Signup.this, "Oops! Some problem occured,Please try again later", Toast.LENGTH_LONG).show();
                                                                        progressDialog.dismiss();
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
