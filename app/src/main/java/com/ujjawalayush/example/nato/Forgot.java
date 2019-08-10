package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    String email;
    EditText editText;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        firebaseAuth=FirebaseAuth.getInstance();
        editText=(EditText)findViewById(R.id.editText7);
        progressDialog=new ProgressDialog(this);

    }

    public void onClick(View view) {
        email=editText.getText().toString().trim();
        if(email.equals("")){
            editText.setError("Enter Your Email-Id");
            editText.requestFocus();
            return;
        }
        progressDialog.setMessage("Sending Verification!Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot.this,"Password reset email has been sent to "+email+" !",Toast.LENGTH_LONG).show();
                    Intent data=new Intent(Forgot.this,MainActivity.class);
                    startActivity(data);
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(Forgot.this,"Failed to send reset email! Please try again!",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    public void onBackPressed() {
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        super.onBackPressed();
    }
}
