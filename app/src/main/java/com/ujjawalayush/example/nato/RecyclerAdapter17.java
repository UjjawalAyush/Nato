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
import android.widget.ImageButton;
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

public class RecyclerAdapter17 extends RecyclerView.Adapter<RecyclerAdapter17.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData17> myList;
    int mLastPosition=0,request_code=1;
    long id2;
    Context c;
    String m;
    int i;
    public RecyclerAdapter17(ArrayList<RecyclerData17> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row17, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.name.setText("Place of Visit: "+myList.get(position).getName());
        holder.by.setText("By : "+myList.get(position).getBy());
        holder.startingDate.setText("Start Date : "+myList.get(position).getStartingDate());
        holder.endingDate.setText("End Date : "+myList.get(position).getEndingDate());
        holder.description.setText("Place of Visit: "+myList.get(position).getDescription());
        if(myList.get(position).getX().equals("0")){
            holder.b.setVisibility(View.INVISIBLE);
            holder.by.setVisibility(View.INVISIBLE);
            holder.average.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != myList?myList.size():0);
    }
    public void notifyData(ArrayList<RecyclerData17> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        TextView startingDate,endingDate,name,description,by,average;
        CircularImageView imageView;
        Button edit;
        ImageButton b;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            name=(TextView)parent.findViewById(R.id.category1);
            description=(TextView)parent.findViewById(R.id.description1);
            by=(TextView)parent.findViewById(R.id.by);
            b=(ImageButton)parent.findViewById(R.id.qwe);
            startingDate=(TextView)parent.findViewById(R.id.startingDate1);
            average=(TextView)parent.findViewById(R.id.average);
            mainLayout=(RelativeLayout)parent.findViewById(R.id.mainLayout);
            endingDate=(TextView)parent.findViewById(R.id.endingDate1);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data =new Intent(v.getContext(),Recommend.class);
                    Bundle extras=new Bundle();
                    extras.putString("trip",myList.get(getAdapterPosition()).getTrip());
                    extras.putString("time",myList.get(getAdapterPosition()).getTime());
                    data.putExtra("extras",extras);
                    v.getContext().startActivity(data);
                }
            });
        }
    }
}

