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

public class RecyclerAdapter18 extends RecyclerView.Adapter<RecyclerAdapter18.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData18> myList;
    int mLastPosition=0,request_code=1;
    long id2;
    Context c;
    String m;
    int i;
    public RecyclerAdapter18(ArrayList<RecyclerData18> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row18, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.reacted.setText("Reacted By: "+myList.get(position).getName());
        holder.rating.setText("Rating: "+myList.get(position).getRating());
        holder.description.setText("Comment: "+myList.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return (null != myList?myList.size():0);
    }
    public void notifyData(ArrayList<RecyclerData18> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        TextView description,reacted,rating;
        CircularImageView imageView;
        Button edit;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            rating=(TextView)parent.findViewById(R.id.rating);
            description=(TextView)parent.findViewById(R.id.description1);
            reacted=(TextView)parent.findViewById(R.id.reaction);
        }
    }
}

