package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    EditText editText, editText1;
    FirebaseAuth firebaseAuth;
    TextView textView, textView1;
    private ProgressDialog progressDialog;
    Uri photoUri;
    FirebaseUser firebaseUser;
    Cursor c, d;
    String dob, current, school, home, schoolp, collegep, college, about;
    DBAdapter db;
    String uid, diplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Intent data = getIntent();
        if (!data.hasExtra("row")) {
            if (user != null && user.isEmailVerified()) {
                Intent data1 = new Intent(MainActivity.this, MainPage.class);
                startActivity(data1);
            }
        }

        if (isNe() == false) {
            Toast.makeText(this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
        }
        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.logo, null);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setBackground(drawable);
        textView = (TextView) findViewById(R.id.textView4);
        textView1 = (TextView) findViewById(R.id.textView3);
        textView.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        editText = (EditText) findViewById(R.id.editText3);
        editText1 = (EditText) findViewById(R.id.editText4);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.INVISIBLE);
                editText.setHint("");
                editText1.setHint("Password");
            }
        });
        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                textView.setVisibility(View.INVISIBLE);
                textView1.setVisibility(View.VISIBLE);
                editText1.setHint("");
                editText.setHint("Email");
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

    public void onClick(View v) {
        String email, password;
        email = editText.getText().toString().trim();
        password = editText1.getText().toString().trim();
        if (email.equals("")) {
            editText.setError("E-Mail entry is mandatory");
            editText.requestFocus();
            return;
        }
        if (password.equals("")) {
            editText1.setError("Password entry is mandatory");
            editText1.requestFocus();
            return;
        }
        progressDialog.setMessage("Logging in!Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert firebaseUser != null;
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent data = new Intent(MainActivity.this, MainPage.class);
                        startActivity(data);

                    } else {
                        Toast.makeText(MainActivity.this, "First verify email and then try again", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid E-Mail Id or Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }


    public void onClick1(View view) {
        Intent data = new Intent(MainActivity.this, Forgot.class);
        startActivity(data);
    }

    public void onClick2(View view) {
        Intent data = new Intent(MainActivity.this, Signup.class);
        startActivity(data);
    }

    public void onBackPressed() {
        if (progressDialog.isShowing()) {
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
