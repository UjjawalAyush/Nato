package com.ujjawalayush.example.nato;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Trip extends AppCompatActivity {
    String trip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolba8);
        Intent data=getIntent();
        trip=data.getStringExtra("trip");
        toolbar.setTitle(trip);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation1);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Fragment fragment=new HomeFragment();
        Bundle data1=new Bundle();
        data1.putString("trip",trip);
        fragment.setArguments(data1);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frame_container1, fragment);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch(menuItem.getItemId()){
                case R.id.home:
                    fragment=new HomeFragment();
                    Bundle data1=new Bundle();
                    data1.putString("trip",trip);
                    fragment.setArguments(data1);
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.frame_container1, fragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.news1:
                    fragment=new ExpenseFragment();
                    Bundle data2=new Bundle();
                    data2.putString("trip",trip);
                    fragment.setArguments(data2);
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.frame_container1, fragment);
                    transaction2.addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.news2:
                    fragment=new RecFragment();
                    Bundle data3=new Bundle();
                    data3.putString("trip",trip);
                    fragment.setArguments(data3);
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.frame_container1, fragment);
                    transaction3.addToBackStack(null);
                    transaction3.commit();
                    return true;
            }
            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu1,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
            Intent data=new Intent(Trip.this,MainPage.class);
            startActivity(data);
    }
    public void onClick2(View v){
        Intent data=new Intent(Trip.this,Schedule.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
    public void onClick1(View v){
        Intent data=new Intent(Trip.this,Invite.class);
        data.putExtra("trip",trip);
        startActivity(data);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
