package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile2 extends AppCompatActivity {
    ProgressDialog progressDialog;
    ImageView imageView;
    TextView college,school,grad,pass,home,current,no,dob,profile,username,about;
    String k,college1,school1,grad1,pass1,home1,current1,no1,dob1,profile1,username1,about1,u;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        progressDialog=new ProgressDialog(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar8);
        toolbar.setTitle("NATO");
        Intent data=getIntent();
        home1=data.getBundleExtra("extras").getString("uid");
        k=data.getBundleExtra("extras").getString("l");
        college1=data.getBundleExtra("extras").getString("q");
        no1=data.getBundleExtra("extras").getString("sent");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
         Fragment fragment=new ProfileFragment();
        Bundle data1=new Bundle();
        data1.putString("uid",home1);
        u=data.getBundleExtra("extras").getString("username");
        data1.putString("username",u);
        data1.putString("sent",no1);

        fragment.setArguments(data1);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frame_container, fragment);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch(menuItem.getItemId()){
                case R.id.profile:
                    fragment=new ProfileFragment();
                    Bundle data1=new Bundle();
                    data1.putString("uid",home1);
                    data1.putString("sent",no1);
                    data1.putString("username",u);

                    fragment.setArguments(data1);
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.frame_container, fragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.news:
                    fragment=new NewsFragment();
                    Bundle data2=new Bundle();
                    data2.putString("uid",home1);
                    data2.putString("sent",no1);
                    data2.putString("username",u);
                    fragment.setArguments(data2);
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.frame_container, fragment);
                    transaction2.addToBackStack(null);
                    transaction2.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        if(k.equals("0")){
            Intent data=new Intent(Profile2.this,AddFriends.class);
            startActivity(data);
        }
        else if(k.equals("1")) {
            Intent data = new Intent(Profile2.this, Friends.class);
            startActivity(data);
        }
        else if(k.equals("2")){
            Intent data = new Intent(Profile2.this, Notifications.class);
            startActivity(data);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
