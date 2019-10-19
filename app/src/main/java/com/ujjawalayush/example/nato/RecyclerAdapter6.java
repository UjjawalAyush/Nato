package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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

public class RecyclerAdapter6 extends RecyclerView.Adapter<RecyclerAdapter6.RecyclerItemViewHolder> {

    private ArrayList<Uri> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;


    public RecyclerAdapter6(ArrayList<Uri> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row4, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        Picasso.get().load(myList.get(position)).centerCrop().fit().into(holder.circularImageView);
    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<Uri> myList,int i) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        if(i==2){
            this.myList.clear();
            myList.clear();
            notifyDataSetChanged();
        }
        notifyDataSetChanged();

    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        public String title, description;
        TextView username, message, senderFooter, receiverText, receiverFooter,date;
        RelativeLayout sender, receiver;
        ImageView circularImageView;
        ImageButton imageButton;
        CardView cardView;

        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            circularImageView=(ImageView)parent.findViewById(R.id.imageView78);
            imageButton=(ImageButton)parent.findViewById(R.id.imageButton23);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    c=v.getContext();
                    myList.remove(i);
                    notifyDataSetChanged();
                    Toast.makeText(c,"Image successfully removed",Toast.LENGTH_SHORT).show();
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
}

