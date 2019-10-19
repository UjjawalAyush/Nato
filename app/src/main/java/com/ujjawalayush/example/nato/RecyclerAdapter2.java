package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData2> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;


    public RecyclerAdapter2(ArrayList<RecyclerData2> myList) {
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
        if(myList.get(position).getRequest().equals("1")){
            holder.person.setVisibility(View.INVISIBLE);
        }
        else{
            holder.person.setImageDrawable(myList.get(position).getDrawable());
        }
        if (myList.get(position).getDisplay1().equals("Home")) {
            holder.value.setText("Home Town : " + myList.get(position).getInfo());
        } else if (myList.get(position).getDisplay1().equals("About")) {
            holder.value.setText("About me: " + myList.get(position).getInfo());
        } else if (myList.get(position).getDisplay1().equals("Current")) {
            holder.value.setText("Lives in " + myList.get(position).getInfo());
        } else if (myList.get(position).getDisplay1().equals("College")) {
            holder.value.setText("College : " + myList.get(position).getInfo());
        } else if (myList.get(position).getDisplay1().equals("School")) {
            holder.value.setText("School : " + myList.get(position).getInfo());
        } else if (myList.get(position).getDisplay1().equals("0")) {
            holder.value.setVisibility(View.INVISIBLE);
        }
        holder.value.setText(myList.get(position).getInfo());
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(position).getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("PhotoUrl")){
                    Picasso.get().load(dataSnapshot.child("PhotoUrl").getValue().toString()).centerCrop().fit().into(holder.edit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<RecyclerData2> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title, description, date;
        TextView value, display;
        CircularImageView edit;
        ImageView person;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            mainLayout=(RelativeLayout)parent.findViewById(R.id.mainLayout1);
            display = (TextView) parent.findViewById(R.id.text11);
            value = (TextView) parent.findViewById(R.id.text12);
            edit = (CircularImageView) parent.findViewById(R.id.circularImageView11);
            person=(ImageView)parent.findViewById(R.id.person);
            person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = v.getContext();
                    i = getAdapterPosition();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if (myList.get(i).getRequest().equals("3")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.Theme_AppCompat_DayNight_Dialog);
                        builder.setTitle("Do you wish to send friend request?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                                databaseReference.child(myList.get(i).getUid()).setValue("Sent");
                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(myList.get(i).getUid());
                                databaseReference1.child(user.getUid()).setValue("Received");
                                Toast.makeText(c, "Friend Request Successfully sent", Toast.LENGTH_SHORT).show();
                                myList.clear();
                                notifyDataSetChanged();
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
                    else if(myList.get(i).getRequest().equals("2")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.Theme_AppCompat_DayNight_Dialog);
                        builder.setTitle("Do you wish to unfriend?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                                databaseReference.child(myList.get(i).getUid()).removeValue();
                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(myList.get(i).getUid());
                                databaseReference1.child(user.getUid()).removeValue();
                                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Friends");
                                databaseReference2.child(myList.get(i).getUid()).removeValue();
                                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(i).getUid()).child("Friends");
                                databaseReference3.child(user.getUid()).removeValue();
                                myList.clear();
                                notifyDataSetChanged();
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
                    else if(myList.get(i).getRequest().equals("0")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.Theme_AppCompat_DayNight_Dialog);
                        builder.setTitle("Do you wish to withdraw friend request?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(user.getUid());
                                databaseReference.child(myList.get(i).getUid()).removeValue();
                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friend Requests").child(myList.get(i).getUid());
                                databaseReference1.child(user.getUid()).removeValue();
                                Toast.makeText(c, "Friend request successfully removed", Toast.LENGTH_SHORT).show();
                                myList.clear();
                                notifyDataSetChanged();
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
                    extras.putString("l","0");
                    extras.putString("sent",myList.get(i).getRequest());
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

    public void UpdateList(ArrayList<RecyclerData2> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}

