package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter11 extends RecyclerView.Adapter<RecyclerAdapter11.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData11> myList;
    int mLastPosition=0,request_code=1;
    long id2;
    Context c;
    String m;
    int i;
    public RecyclerAdapter11(ArrayList<RecyclerData11> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row11, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.date.setVisibility(View.INVISIBLE);
        if(myList.get(position).getTrip().equals("Trip")&&myList.get(position).getMessage().equals("Friend Request")){
            holder.value.setText(myList.get(position).getUsername()+" sent you a friend request");
        }
        else if(myList.get(position).getMessage().equals("Invited")){
            holder.value.setText(myList.get(position).getUsername()+" invited you to join "+myList.get(position).getTrip()+" Group");

        }
        else if(myList.get(position).getMessage().equals("Accepted")){
            holder.value.setText(myList.get(position).getUsername()+" accepted your request to join "+myList.get(position).getTrip()+" Group");

        }
        else if(myList.get(position).getMessage().equals("Rejected")){
            holder.value.setText(myList.get(position).getUsername()+" rejected your request to join "+myList.get(position).getTrip()+" Group");

        }
        else if(myList.get(position).getMessage().equals("joined")) {
            holder.value.setText(myList.get(position).getUsername()+" joined the "+myList.get(position).getTrip()+" Group");

        }
        else if(myList.get(position).getMessage().equals("left")){
            holder.value.setText(myList.get(position).getUsername()+" left the "+myList.get(position).getTrip()+" Group");
        }
        else{
            holder.value.setVisibility(View.INVISIBLE);
        }
        if(!myList.get(position).getDate().equals("")){
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(myList.get(position).getDate());
        }
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(position).getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("PhotoUrl")){
                    Picasso.get().load(dataSnapshot.child("PhotoUrl").getValue().toString()).centerCrop().fit().into(holder.imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != myList?myList.size():0);
    }
    public void notifyData(ArrayList<RecyclerData11> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title, description;
        TextView value,display,date;
        Button edit;
        ImageView imageView;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            mainLayout=(RelativeLayout)parent.findViewById(R.id.mainLayout11);
            imageView=(ImageView)parent.findViewById(R.id.circularImage1);
            value=(TextView)parent.findViewById(R.id.text1);
            date=(TextView)parent.findViewById(R.id.time1);

            mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = getAdapterPosition();
                        c = v.getContext();
                        if (isNe()) {
                            if (myList.get(i).getTrip().equals("Trip") && myList.get(i).getMessage().equals("Friend Request")) {
                                Intent data = new Intent(v.getContext(), Profile2.class);
                                Bundle extras = new Bundle();
                                extras.putString("username", myList.get(i).getUsername());
                                extras.putString("uid", myList.get(i).getUid());
                                extras.putString("l", "2");
                                extras.putString("sent", "1");
                                data.putExtra("extras", extras);
                                v.getContext().startActivity(data);
                            } else if ((!myList.get(i).getTrip().equals("Trip")) && myList.get(i).getMessage().equals("Invited")) {
                                Intent data = new Intent(v.getContext(), TripInfo.class);
                                Bundle extras = new Bundle();
                                extras.putString("username", myList.get(i).getUsername());
                                extras.putString("uid", myList.get(i).getUid());
                                extras.putString("message", myList.get(i).getMessage());
                                extras.putString("trip", myList.get(i).getTrip());
                                data.putExtra("extras", extras);
                                v.getContext().startActivity(data);
                            } else if ((!myList.get(i).getTrip().equals("Trip")) && myList.get(i).getMessage().equals("joined")) {
                                Intent data = new Intent(v.getContext(), Trip.class);
                                data.putExtra("trip", myList.get(i).getTrip());
                                v.getContext().startActivity(data);
                            }
                        }
                        else{
                            Toast.makeText(c,"Please check your internet connection",Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }

    }
    public boolean isNe(){
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }
        catch(NullPointerException e){
            return false;
        }
    }
}

