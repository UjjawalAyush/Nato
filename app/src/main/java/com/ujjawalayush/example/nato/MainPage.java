package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SearchView searchView;
    ViewPager viewPager;
    TabAdapter tabAdapter;
    int f=0;
    String row;
    TabLayout tabLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        navigationView=(NavigationView)findViewById(R.id.navigationView);
        View header=navigationView.getHeaderView(0);
        TextView textView=header.findViewById(R.id.textView11);
        TextView textView1=header.findViewById(R.id.textView12);
        CircularImageView circularImageView=header.findViewById(R.id.circularImageView2);
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllContacts();
        c.moveToPosition(0);
        row=c.getString(0);
        textView.setText("Welcome "+c.getString(3));
        textView1.setText(c.getString(4));
        String string=new String(c.getBlob(5));
        if(!string.equals("0")){
            Bitmap bitmap=getImage(c.getBlob(5));
            circularImageView.setImageBitmap(bitmap);
        }
        db.close();
        navigationView.setNavigationItemSelectedListener(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar19);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        toolbar.setTitle("NATO");
        setSupportActionBar(toolbar);
        drawerLayout= (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        tabAdapter=new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        searchView = (android.support.v7.widget.SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch(item.getItemId()){
            case R.id.friends:
                Intent data=new Intent(MainPage.this,Friends.class);
                startActivity(data);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile:
                Intent data=new Intent(MainPage.this,Profile1.class);
                data.putExtra("row",row);
                startActivity(data);
            case R.id.signout:
                DBAdapter db=new DBAdapter(this);
                db.open();
                db.deleteAllContacts();
                db.close();
                Intent data2=new Intent(MainPage.this,MainActivity.class);
                data2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(data2);
                finish();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true;
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public void onBackPressed() {
        f++;
        if(f==1){
            Toast.makeText(this,"Press again to Exit",Toast.LENGTH_LONG).show();
        }
        else{
            Intent newIntent = new Intent(this,QuitAppActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            finish();
        }
    }
}
