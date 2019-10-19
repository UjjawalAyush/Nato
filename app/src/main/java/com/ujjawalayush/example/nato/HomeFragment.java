package com.ujjawalayush.example.nato;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    TextView money,leave;
    RecyclerView recyclerView;
    ArrayList<RecyclerData> myList=new ArrayList<>();
    RecyclerAdapter9 mAdapter;
    ImageView imageView;
    ProgressDialog progressDialog;
    String idd;
    long x=0;
    FirebaseUser user;
    String trip,type;

    String id;
    View home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_home, container, false);
        trip=getArguments().getString("trip");
        leave=(TextView)home.findViewById(R.id.leave);
        money=(TextView)home.findViewById(R.id.money);
        recyclerView=(RecyclerView)home.findViewById(R.id.recyclerVie4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter9(myList);
        recyclerView.setAdapter(mAdapter);
        imageView=(ImageView)home.findViewById(R.id.ima);
        DBAdapter db=new DBAdapter(home.getContext());
        db.open();
        Cursor c=db.getAllTrips();
        user=FirebaseAuth.getInstance().getCurrentUser();
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(3).equals(trip)){
                type=c.getString(1);
                id=c.getString(0);
                break;
            }
            c.moveToNext();
        }
        RecyclerData mLog1=new RecyclerData();
        mLog1.setValue(user.getUid());
        mLog1.setDisplay(user.getDisplayName());
        myList.add(mLog1);
        mAdapter.notifyData(myList);
        Cursor d=db.getAllMembers();
        d.moveToFirst();
        while(!d.isAfterLast()){
            if(d.getString(1).equals(trip)){
                RecyclerData mLog=new RecyclerData();
                mLog.setValue(d.getString(2));
                mLog.setDisplay(d.getString(3));
                myList.add(mLog);
                mAdapter.notifyData(myList);
            }
            d.moveToNext();
        }
        progressDialog=new ProgressDialog(home.getContext());
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("photo")){
                    Picasso.get().load(dataSnapshot.child("photo").getValue().toString()).centerCrop().fit().into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(home.getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                builder.setTitle("Do you wish to Leave?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("Loading! Please Wait...");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        final String formattedDate = df.format(c);
                        Calendar cal = Calendar.getInstance();
                        Date date=cal.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                        final String formattedTime=dateFormat.format(date);
                        DatabaseReference databaseReference12 = FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("Notifications");
                        final String k = databaseReference12.push().getKey();
                        DBAdapter db=new DBAdapter(home.getContext());
                        db.open();
                        Cursor d=db.getAllMembers();
                        if(d.getCount()>0){
                            d.moveToFirst();
                            while(!d.isAfterLast()){
                                if(d.getString(1).equals(trip)){
                                    db.deleteMember(Long.parseLong(d.getString(0)));
                                }
                                d.moveToNext();
                            }
                        }
                        databaseReference12.child(k).child("Username").setValue(user.getDisplayName());
                        databaseReference12.child(k).child("Uid").setValue(user.getUid());
                        databaseReference12.child(k).child("Message").setValue("left");
                        databaseReference12.child(k).child("type").setValue(type);
                        databaseReference12.child(k).child("date").setValue(formattedDate+" "+formattedTime);
                        DatabaseReference databaseReference13 = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
                        databaseReference13.child(trip).removeValue();
                        final DatabaseReference databaseReference131 = FirebaseDatabase.getInstance().getReference();
                        progressDialog.dismiss();
                        DBAdapter db1=new DBAdapter(home.getContext());
                        db1.open();
                        db1.deleteTrip(Long.parseLong(id));
                        databaseReference131.child("Trips").child(trip).child("members").child(user.getUid()).removeValue();
                        databaseReference131.child("Trips").child(trip).child("Details").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String a=dataSnapshot.child("members").getValue().toString();
                                long b=Long.parseLong(a);
                                b--;
                                a=Long.toString(b);
                                databaseReference131.child("Trips").child(trip).child("Details").child("members").setValue(a);
                                Intent data = new Intent(home.getContext(), MainPage.class);
                                startActivity(data);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                       dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return home;
    }

}
