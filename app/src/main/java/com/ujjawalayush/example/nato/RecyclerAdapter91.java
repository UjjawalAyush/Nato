package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.Intent;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter91 extends RecyclerView.Adapter<RecyclerAdapter91.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData91> myList;
    int mLastPosition=0,request_code=1;
    long id2;
    Context c;
    String m;
    int i;
    public RecyclerAdapter91(ArrayList<RecyclerData91> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row91, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.display.setText(myList.get(position).getDiplay()+" owes you 695 bucks");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(position).getValue());
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
    public void notifyData(ArrayList<RecyclerData91> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title, description, date;
        TextView value,display;
        CircularImageView imageView;
        Button edit;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            display=(TextView)parent.findViewById(R.id.textq);
            imageView=(CircularImageView)parent.findViewById(R.id.circu);
            edit=(Button)parent.findViewById(R.id.b);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    display.setText(myList.get(getAdapterPosition()).getDiplay()+" owes u nothing");
                    Toast.makeText(v.getContext(),"Settled",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

