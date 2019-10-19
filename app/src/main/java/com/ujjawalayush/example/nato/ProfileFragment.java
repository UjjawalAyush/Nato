package com.ujjawalayush.example.nato;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }
    ImageView imageView;
    TextView college,school,grad,pass,home,current,no,dob,profile,username,about,request;
    String k,college1,school1,grad1,pass1,home1,current1,no1,dob1,profile1,username1,about1;
    FirebaseUser user;
    Button q;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile2, container, false);
        progressDialog=new ProgressDialog(v.getContext());
        progressDialog.setMessage("Loading!Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        imageView = (ImageView) v.findViewById(R.id.u);
        imageView.setVisibility(View.INVISIBLE);
        username = (TextView) v.findViewById(R.id.username);
        no = (TextView) v.findViewById(R.id.no);
        dob = (TextView) v.findViewById(R.id.dob);
        college = (TextView) v.findViewById(R.id.about_me1);
        request = (TextView) v.findViewById(R.id.username1);
        school = (TextView) v.findViewById(R.id.about_me4);
        pass = (TextView) v.findViewById(R.id.about_me3);
        grad = (TextView) v.findViewById(R.id.about_me2);
        home = (TextView) v.findViewById(R.id.about_me5);
        current = (TextView) v.findViewById(R.id.about_me6);
        about = (TextView) v.findViewById(R.id.about_me);
        q=(Button)v.findViewById(R.id.q);
        k=getArguments().getString("uid");
        no1=getArguments().getString("sent");
        about1=getArguments().getString("username");
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(v.getContext(),chats.class);
                Bundle extras=new Bundle();
                extras.putString("username",about1);
                extras.putString("uid",k);
                data.putExtra("extras",extras);
                startActivity(data);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(no1.equals("2")){
            request.setText("FRIENDS");
        }
        else if(no1.equals("1")){
            request.setText("Friend request received");
        }
        else if(no1.equals("0")){
            request.setText("Friend request sent");
        }
        else{
            request.setText("Add friend");
        }
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(no1.equals("2")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Do you wish to unfriend?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                            databaseReference.child(k).removeValue();
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(k);
                            databaseReference1.child(user.getUid()).removeValue();
                            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Friends");
                            databaseReference2.child(k).removeValue();
                            DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Users").child(k).child("Friends");
                            databaseReference3.child(user.getUid()).removeValue();
                            request.setText("Add friend");
                            Toast.makeText(v.getContext(), "Friend Successfully unfriended", Toast.LENGTH_SHORT).show();

                            no1="1";
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else if(no1.equals("0")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Do you wish to withdraw friend request?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                            databaseReference.child(k).removeValue();
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(k);
                            databaseReference1.child(user.getUid()).removeValue();
                            Toast.makeText(v.getContext(), "Friend request successfully removed", Toast.LENGTH_SHORT).show();
                            request.setText("Add Friend");
                            no1="1";
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else if(no1.equals("3")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Do you wish to send friend request?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                            databaseReference.child(k).setValue("Sent");
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(k);
                            databaseReference1.child(user.getUid()).setValue("Received");
                            Toast.makeText(v.getContext(), "Friend Request Successfully sent", Toast.LENGTH_SHORT).show();
                            request.setText("Friend request sent");
                            no1="0";
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("What do you wanna do?");
                    builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                            databaseReference.child(k).setValue("Friends");
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(k);
                            databaseReference1.child(user.getUid()).setValue("Friends");
                            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Friends");
                            databaseReference2.child(k).setValue("Yes");
                            DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Users").child(k).child("Friends");
                            databaseReference3.child(user.getUid()).setValue("Yes");
                            request.setText("FRIENDS");
                            Toast.makeText(v.getContext(), "Friend Request Successfully accepted", Toast.LENGTH_SHORT).show();
                            DBAdapter db=new DBAdapter(v.getContext());
                            db.open();
                            long id=-1;
                            Cursor d=db.getAllNotifications();
                            d.moveToFirst();
                            if(d.getCount()>0){
                                while(!d.isAfterLast()){
                                    if(d.getString(4).equals("Friend Request")&&d.getString(1).equals(k)){
                                        id=Long.parseLong(d.getString(0));
                                        break;
                                    }
                                    d.moveToNext();
                                }
                            }
                            if(id!=-1){
                                db.deleteNotifications(id);
                            }
                            no1="2";
                        }
                    });
                    builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                            databaseReference.child(k).removeValue();
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(k);
                            databaseReference1.child(user.getUid()).removeValue();
                            Toast.makeText(v.getContext(), "Friend Request Successfully rejected", Toast.LENGTH_SHORT).show();
                            request.setText("Add Friend");
                            DBAdapter db=new DBAdapter(v.getContext());
                            db.open();
                            long id=-1;
                            Cursor d=db.getAllNotifications();
                            d.moveToFirst();
                            if(d.getCount()>0){
                                while(!d.isAfterLast()){
                                    if(d.getString(4).equals("Friend Request")&&d.getString(1).equals(k)){
                                        id=Long.parseLong(d.getString(0));
                                        break;
                                    }
                                    d.moveToNext();
                                }
                            }
                            if(id!=-1){
                                db.deleteNotifications(id);
                            }
                            no1="3";

                        }
                    });
                    builder.show();
                }
            }
        });
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(k);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("PhotoUrl")) {
                    String photo = dataSnapshot.child("PhotoUrl").getValue().toString();
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(Uri.parse(photo)).centerCrop().fit().into(imageView);
                }
                else{
                    imageView.setVisibility(View.VISIBLE);
                }
                if (dataSnapshot.hasChild("Success")) {
                    no.setText("No. od successful trips: " + dataSnapshot.child("Success").getValue().toString());
                }
                if (dataSnapshot.hasChild("Username")) {
                    username.setText("Username: " + dataSnapshot.child("Username").getValue().toString());
                }
                if (dataSnapshot.hasChild("Dob")) {
                    dob.setText("Wish me on " + dataSnapshot.child("Dob").getValue().toString());
                }
                if (dataSnapshot.hasChild("College")) {
                    college.setText("College: " + dataSnapshot.child("College").getValue().toString());
                }
                if (dataSnapshot.hasChild("School")) {
                    school.setText("School: " + dataSnapshot.child("School").getValue().toString());
                }
                if (dataSnapshot.hasChild("Current")) {
                    current.setText("Current City: " + dataSnapshot.child("Current").getValue().toString());
                }
                if (dataSnapshot.hasChild("Home")) {
                    home.setText("Home: " + dataSnapshot.child("Home").getValue().toString());
                }
                if (dataSnapshot.hasChild("CollegeP")) {
                    grad.setText("Year of Graduation: " + dataSnapshot.child("CollegeP").getValue().toString());
                }
                if (dataSnapshot.hasChild("SchoolP")) {
                    pass.setText("Year of passing: " + dataSnapshot.child("SchoolP").getValue().toString());
                }
                if (dataSnapshot.hasChild("About")) {
                    about.setText("" + dataSnapshot.child("About").getValue().toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
