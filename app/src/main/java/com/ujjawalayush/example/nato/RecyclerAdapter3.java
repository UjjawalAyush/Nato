package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData3> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    Resources res;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;


    public RecyclerAdapter3(ArrayList<RecyclerData3> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.display.setText(myList.get(position).getUsername());
        if (myList.get(position).getkey().equals("Home")) {
            holder.value.setText("Home Town : " + myList.get(position).getInfo());
        } else if (myList.get(position).getkey().equals("About")) {
            holder.value.setText("About me: " + myList.get(position).getInfo());
        } else if (myList.get(position).getkey().equals("Current")) {
            holder.value.setText("Lives in " + myList.get(position).getInfo());
        } else if (myList.get(position).getkey().equals("College")) {
            holder.value.setText("College : " + myList.get(position).getInfo());
        } else if (myList.get(position).getkey().equals("School")) {
            holder.value.setText("School : " + myList.get(position).getInfo());
        } else if (myList.get(position).getkey().equals("0")) {
            holder.value.setVisibility(View.INVISIBLE);
        }
        holder.value.setText(myList.get(position).getInfo());
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(position).getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("PhotoUrl")){
                    Picasso.get().load(Uri.parse(dataSnapshot.child("PhotoUrl").getValue().toString())).centerCrop().fit().into(holder.edit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.person.setImageDrawable(ResourcesCompat.getDrawable(res,R.drawable.ic_people_black_24dp,null));

    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<RecyclerData3> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title, description, date;
        TextView value, display;
        CircularImageView edit,edit1;
        ImageView person;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            mainLayout=(RelativeLayout)parent.findViewById(R.id.mainLayout1);
            display = (TextView) parent.findViewById(R.id.text11);
            value = (TextView) parent.findViewById(R.id.text12);
            edit = (CircularImageView) parent.findViewById(R.id.circularImageView11);
            edit1 = (CircularImageView) parent.findViewById(R.id.circularImageView1156);

            person=(ImageView)parent.findViewById(R.id.person);
            res=parent.getResources();
            person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c=v.getContext();
                    i=getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Do you wish to unfriend ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(myList.get(i).getK());
                            databaseReference.child(myList.get(i).getUid()).removeValue();
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(myList.get(i).getUid());
                            databaseReference1.child(myList.get(i).getK()).removeValue();
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(i).getUid()).child("Friends");
                            databaseReference2.child(myList.get(i).getK()).removeValue();
                            DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(i).getK()).child("Friends");
                            databaseReference3.child(myList.get(i).getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(c, "Successfully unfriended", Toast.LENGTH_SHORT).show();
                                }
                            });

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
            });
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    Intent data=new Intent(v.getContext(),Profile2.class);
                    Bundle extras=new Bundle();
                    extras.putString("username",myList.get(i).getUsername());
                    extras.putString("uid",myList.get(i).getUid());
                    extras.putString("l","1");
                    extras.putString("sent","2");
                    data.putExtra("extras",extras);
                    v.getContext().startActivity(data);
                }
            });
        }
    }

    public void clear() {
        for(int i = 0;i<myList.size();i++)
        {
            myList.remove(i);
        }
    }

    public void UpdateList(ArrayList<RecyclerData3> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}

