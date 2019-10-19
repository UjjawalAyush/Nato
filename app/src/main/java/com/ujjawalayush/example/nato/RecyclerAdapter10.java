package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter10 extends RecyclerView.Adapter<RecyclerAdapter10.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData10> myList;
    int mLastPosition=0,request_code=1;
    long id2;
    Context c;
    String m;
    int i;
    public RecyclerAdapter10(ArrayList<RecyclerData10> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row10, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.type.setText("Type : "+myList.get(position).getType());
        holder.decription.setText("Description : "+myList.get(position).getAdd());
        holder.status.setText("Status : "+myList.get(position).getStatus());
        holder.expected.setText(myList.get(position).getExpected());
        holder.date.setText(myList.get(position).getDate());
        holder.member.setText("No.of members : "+myList.get(position).getMember());
        holder.name.setText("Name : "+myList.get(position).getName());
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips").child(myList.get(position).getName()).child("Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("photo")){
                    Picasso.get().load(Uri.parse(dataSnapshot.child("photo").getValue().toString())).into(holder.circularImageView);
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
    public void notifyData(ArrayList<RecyclerData10> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title;
        TextView type,date,member,expected,decription,name,status;
        Button edit;
        CircularImageView circularImageView;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            type=(TextView)parent.findViewById(R.id.text1112);
            date=(TextView)parent.findViewById(R.id.date2);
            member=(TextView)parent.findViewById(R.id.text12);
            expected=(TextView)parent.findViewById(R.id.date);
            decription=(TextView)parent.findViewById(R.id.des);
            name=(TextView)parent.findViewById(R.id.text121);
            status=(TextView)parent.findViewById(R.id.status);
            mainLayout=(RelativeLayout)parent.findViewById(R.id.main3);
            circularImageView=(CircularImageView)parent.findViewById(R.id.circular2);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    c=v.getContext();
                    if(myList.get(0).getFragment().equals("0")) {
                        Intent data = new Intent(c, Trip.class);
                        data.putExtra("trip", myList.get(i).getName());
                        c.startActivity(data);
                    }
                    else{
                        Intent data = new Intent(c, Trip1.class);
                        data.putExtra("trip", myList.get(i).getName());
                        c.startActivity(data);
                    }
                }
            });
        }
    }
    public void UpdateList(ArrayList<RecyclerData10> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}

