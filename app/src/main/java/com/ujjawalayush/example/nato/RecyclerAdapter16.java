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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerAdapter16 extends RecyclerView.Adapter<RecyclerAdapter16.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData16> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;


    public RecyclerAdapter16(ArrayList<RecyclerData16> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row16, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.money.setText("Total Money Spent: "+myList.get(position).getMoney());
        holder.description.setText("Description: "+myList.get(position).getDescription());
        holder.category.setText("Category: "+myList.get(position).getCategory());
        holder.date.setText("Total Money Spent : "+myList.get(position).getDate());
        holder.members.setText("Money Spent on " +myList.get(position).getMembers()+" people");



    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<RecyclerData16> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        TextView money,description,date,category,members,members1;
        Button b;

        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            money=(TextView)parent.findViewById(R.id.money1);
            members1=(TextView)parent.findViewById(R.id.members1);
            description=(TextView)parent.findViewById(R.id.description1);
            category=(TextView)parent.findViewById(R.id.category1);
            date=(TextView)parent.findViewById(R.id.time);
            mainLayout=(RelativeLayout)parent.findViewById(R.id.mainLayout);
            members=(TextView)parent.findViewById(R.id.members);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Sorry no receipt/bill uploaded",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

