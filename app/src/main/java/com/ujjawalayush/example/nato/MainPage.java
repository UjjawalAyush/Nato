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
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SearchView searchView;
    ViewPager viewPager;
    TabAdapter tabAdapter;
    int f=0;
    ArrayList<String> hiList,biList;
    ArrayList<String> fiList,miList,xiList;
    ProgressDialog progressDialog;
    FirebaseUser user;
    String row;
    TabLayout tabLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);


        navigationView=(NavigationView)findViewById(R.id.navigationView);
        View header=navigationView.getHeaderView(0);
        final ProgressBar progressBar=(ProgressBar)header.findViewById(R.id.progress);
        final TextView textView=header.findViewById(R.id.textView11);
        final TextView textView1=header.findViewById(R.id.textView12);
        hiList=new ArrayList<>();
        biList=new ArrayList<>();
        fiList=new ArrayList<>();
        miList=new ArrayList<>();
        xiList=new ArrayList<>();

        fiList.clear();
        hiList.clear();
        biList.clear();
        miList.clear();
        xiList.clear();

        final CircularImageView circularImageView=header.findViewById(R.id.circularImageView2);
        final CircularImageView circularImageView1=header.findViewById(R.id.circularImageView23);
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllContacts1();
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton5);
        if(c.getCount()>1) {
            c.moveToFirst();
            final String x = c.getString(1);
            c.moveToNext();
            while(!c.isAfterLast()){
                StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("Images1").child(x);
                storageReference.child(c.getString(1)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Posts").child(x).child("Pictures");
                        databaseReference.setValue(uri.toString());
                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Posts1").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(x).child("Pictures");
                        databaseReference1.setValue(uri.toString());
                    }
                });
                c.moveToNext();
            }
        }

        db.deleteAllContacts1();
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
        progressBar.setVisibility(View.INVISIBLE);
        long h=0;
        DBAdapter db1=new DBAdapter(this);
        db1.open();
        Cursor f=db1.getAllNotifications();
        f.moveToFirst();
        if(f.getCount()>0){
            while(!f.isAfterLast()){
                if(f.getString(4).equals("Friend Request")){
                    hiList.add(f.getString(1));
                }
                else if(f.getString(4).equals("Invited")) {
                    fiList.add(f.getString(1)+" "+f.getString(2));
                }
                else if(f.getString(4).equals("left")) {
                    xiList.add(f.getString(2)+" "+f.getString(1));
                }
                    f.moveToNext();
            }
        }
        Cursor g=db1.getAllMembers();
        g.moveToFirst();
        if(g.getCount()>0){
            while(!g.isAfterLast()){
                    miList.add(g.getString(1)+" "+g.getString(2));
                g.moveToNext();
            }
        }
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(isNe()) {
            DatabaseReference databaseReference1234=FirebaseDatabase.getInstance().getReference().child("Trips");
            databaseReference1234.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        final DatabaseReference a=FirebaseDatabase.getInstance().getReference().child("Trips").child(dataSnapshot1.getKey()).child("Notifications");
                        final String trip=dataSnapshot1.getKey();
                       Query query=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("members").orderByKey().equalTo(user.getUid());
                       query.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if(dataSnapshot.getChildrenCount()>0){
                                   a.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                               final String l=dataSnapshot2.getKey();
                                               a.child(l).addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       final String uid = dataSnapshot.child("Uid").getValue().toString();
                                                       if (!uid.equals(user.getUid())) {
                                                           final String message = dataSnapshot.child("Message").getValue().toString();
                                                           final String username = dataSnapshot.child("Username").getValue().toString();
                                                           final String date = dataSnapshot.child("date").getValue().toString();
                                                           if (message.equals("joined")) {
                                                               if (!miList.contains(trip + " " + uid)) {
                                                                   Toast.makeText(MainPage.this, "You have new notifications", Toast.LENGTH_SHORT).show();
                                                                   floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightBlue));
                                                                   floatingActionButton.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                                                                   DBAdapter db = new DBAdapter(MainPage.this);
                                                                   db.open();
                                                                   db.insertNotifications(uid, trip, username, "joined", date);
                                                                   db.insertMember(trip, uid, username, "Trip");
                                                                   db.close();
                                                               }
                                                           } else if (message.equals("left")) {
                                                               if (!xiList.contains(trip + " " + uid)) {
                                                                   Toast.makeText(MainPage.this, "You have new notifications", Toast.LENGTH_SHORT).show();
                                                                   floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightBlue));
                                                                   floatingActionButton.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                                                                   DBAdapter db = new DBAdapter(MainPage.this);
                                                                   db.open();
                                                                   db.insertNotifications(uid, trip, username, "left", date);
                                                                   Cursor c = db.getAllMembers();
                                                                   c.moveToFirst();
                                                                   String id2 = "-1";
                                                                   while (!c.isAfterLast()) {
                                                                       if (c.getString(1).equals(trip) && c.getString(2).equals(uid)) {
                                                                           id2 = c.getString(0);
                                                                           break;
                                                                       }
                                                                       c.moveToNext();
                                                                   }
                                                                   if (!id2.equals("-1")) {
                                                                       db.deleteMember(Long.parseLong(id2));
                                                                   }
                                                                   db.close();
                                                               }
                                                           }
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("PhotoUrl")) {
                        Picasso.get().load(Uri.parse(dataSnapshot.child("PhotoUrl").getValue().toString())).into(circularImageView);
                        textView.setText("Welcome " + dataSnapshot.child("Username").getValue().toString());
                        textView1.setText(dataSnapshot.child("Number").getValue().toString());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference databaseReference14=FirebaseDatabase.getInstance().getReference().child("Invitations");
            databaseReference14.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        final String l=dataSnapshot1.getKey();
                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Invitations").child(l).child(user.getUid());
                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                    final String k=dataSnapshot1.getKey();
                                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Invitations").child(l).child(user.getUid()).child(k);
                                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(!fiList.contains(k+" "+dataSnapshot.child("Trip").getValue().toString())) {
                                                if (dataSnapshot.child("Status").getValue().toString().equals("Invited")) {
                                                    RecyclerData mLog = new RecyclerData();
                                                    mLog.setDisplay(k);
                                                    mLog.setValue(dataSnapshot.child("Trip").getValue().toString());
                                                    Toast.makeText(MainPage.this, "You have new notifications", Toast.LENGTH_SHORT).show();
                                                    floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightBlue));
                                                    floatingActionButton.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                                                    DBAdapter db = new DBAdapter(MainPage.this);
                                                    db.open();
                                                    db.insertNotifications(k, dataSnapshot.child("Trip").getValue().toString(), dataSnapshot.child("Username").getValue().toString(), "Invited","");
                                                    db.close();
                                                } else if (dataSnapshot.child("Status").getValue().toString().equals("Rejected")) {
                                                    Toast.makeText(MainPage.this, "You have new notifications", Toast.LENGTH_SHORT).show();
                                                    floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightBlue));
                                                    floatingActionButton.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                                                    DBAdapter db = new DBAdapter(MainPage.this);
                                                    db.open();
                                                    db.insertNotifications(k, dataSnapshot.child("Trip").getValue().toString(), dataSnapshot.child("Username").getValue().toString(), "Rejected","");
                                                    db.close();
                                                } else if (dataSnapshot.child("Status").getValue().toString().equals("Accepted")) {
                                                    Toast.makeText(MainPage.this, "You have new notifications", Toast.LENGTH_SHORT).show();
                                                    floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightBlue));
                                                    floatingActionButton.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                                                    DBAdapter db = new DBAdapter(MainPage.this);
                                                    db.open();
                                                    db.insertNotifications(k, dataSnapshot.child("Trip").getValue().toString(), dataSnapshot.child("Username").getValue().toString(), "Accepted","");
                                                    db.close();
                                                }
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

            DatabaseReference databaseReference12 = FirebaseDatabase.getInstance().getReference().child("Chat").child(user.getUid());
            databaseReference12.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                        Toast.makeText(MainPage.this,"You have new messages",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference databaseReference154=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
            progressDialog=new ProgressDialog(MainPage.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            databaseReference154.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0){
                        DBAdapter db=new DBAdapter(MainPage.this);
                        db.open();
                        Cursor d=db.getAllTrips();
                        if(d.getCount()>0) {
                            d.moveToFirst();
                            while(!d.isAfterLast()){
                                biList.add(d.getString(3));
                                d.moveToNext();
                            }
                        }
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                            if (!biList.contains(dataSnapshot1.getKey())){
                                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Trips").child(dataSnapshot1.getKey()).child("members");
                                final String k=dataSnapshot1.getKey();
                                Query query=databaseReference2.orderByKey().equalTo(user.getUid());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getChildrenCount()>0){
                                            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Trips").child(k).child("Details");
                                            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    DBAdapter db=new DBAdapter(MainPage.this);
                                                    db.open();
                                                    db.insertTrip(dataSnapshot.child("type").getValue().toString(),dataSnapshot.child("date").getValue().toString(),k,dataSnapshot.child("description").getValue().toString(),dataSnapshot.child("Status").getValue().toString(),dataSnapshot.child("Expected").getValue().toString(),dataSnapshot.child("members").getValue().toString());
                                                    progressDialog.dismiss();

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else{
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            else{
                                progressDialog.dismiss();

                            }
                        }
                    }
                    else{
                        progressDialog.dismiss();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference databaseReference123 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
            Query query=databaseReference123.orderByValue().equalTo("Received");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0){
                        Toast.makeText(MainPage.this,"You have new notifications",Toast.LENGTH_SHORT).show();
                        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightBlue));
                        floatingActionButton.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            final String k=dataSnapshot1.getKey();
                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Users").child(k);
                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final String username=dataSnapshot.child("Username").getValue().toString();
                                    if(!hiList.contains(k)){
                                        DBAdapter db=new DBAdapter(MainPage.this);
                                        db.open();
                                        db.insertNotifications(k,"Trip",username,"Friend Request","");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.profile:
                Intent data=new Intent(MainPage.this,Profile1.class);
                startActivity(data);
                break;
            case R.id.signout:
                DBAdapter db=new DBAdapter(this);
                db.open();
                db.deleteAllContacts();
                db.deleteAllContacts1();
                db.deleteAllContacts2();
                db.deleteAllContacts3();
                db.deleteAllNotification();
                db.deleteAllNotifications();
                db.deleteAllMembers();
                db.deleteAllTrips();
                db.deleteAllX();
                Intent data2=new Intent(MainPage.this,MainActivity.class);
                data2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                data2.putExtra("row",row);
                startActivity(data2);
                finish();
                break;
        }
        return true;
    }
    public boolean isNe(){
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }
        catch(NullPointerException e){
            return false;
        }
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
            DBAdapter db=new DBAdapter(this);
            db.open();
            db.deleteAllContacts();
            db.deleteAllContacts1();
            db.deleteAllContacts2();
            db.deleteAllContacts3();
            db.deleteAllNotification();
            db.deleteAllNotifications();
            db.deleteAllMembers();
            db.deleteAllTrips();
            db.deleteAllX();
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);

            finish();
        }
    }

    public void onClick4(View view) {
        Intent data=new Intent(MainPage.this,Friends.class);

startActivity(data);
    }
    public void onClick5(View view) {
        Intent data=new Intent(MainPage.this,ChatMain.class);

        startActivity(data);
    }

    public void onClick45(View view) {
        Intent data=new Intent(MainPage.this,Pictures.class);
        DBAdapter db=new DBAdapter(this);
        db.open();
        db.deleteAllContacts();
        db.deleteAllContacts1();
        db.close();
        startActivity(data);
    }

    public void onClick6(View view) {
        Intent data=new Intent(MainPage.this,Notifications.class);
        if(isNe()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            DBAdapter db12 = new DBAdapter(this);
            db12.open();
            Cursor cq = db12.getAllNotifications();
            cq.moveToFirst();
            if (cq.getCount() > 0) {
                while (!cq.isAfterLast()) {
                    if (cq.getString(4).equals("Rejected") || cq.getString(4).equals("Accepted") || cq.getString(4).equals("Invited")) {
                        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Invitations").child(cq.getString(2)).child(user.getUid()).child(cq.getString(1));
                        databaseReference3.removeValue();
                    }
                    cq.moveToNext();
                }
            }
            progressDialog.dismiss();
        }
        startActivity(data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isNe()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            DBAdapter db12 = new DBAdapter(this);
            db12.open();
            Cursor cq = db12.getAllNotifications();
            cq.moveToFirst();
            if (cq.getCount() > 0) {
                while (!cq.isAfterLast()) {
                    if (cq.getString(4).equals("Rejected") || cq.getString(4).equals("Accepted") || cq.getString(4).equals("Invited")) {
                        final String k=cq.getString(2);
                        final String uid=cq.getString(1);
                        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Invitations").child(k).child(user.getUid()).child(uid);
                        databaseReference3.removeValue();
                    }
                    cq.moveToNext();
                }
            }
            progressDialog.dismiss();
        }
    }

}
