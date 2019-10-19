package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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

public class RecyclerAdapter5 extends RecyclerView.Adapter<RecyclerAdapter5.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData5> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;


    public RecyclerAdapter5(ArrayList<RecyclerData5> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.message.setText(myList.get(position).getInfo());
        holder.username.setText(myList.get(position).getUsername());
        holder.date.setText(myList.get(position).getDate());
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(myList.get(position).getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("PhotoUrl")){
                    Picasso.get().load(dataSnapshot.child("PhotoUrl").getValue().toString()).centerCrop().fit().into(holder.circularImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<RecyclerData5> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();

    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        public String title, description;
        TextView username, message, senderFooter, receiverText, receiverFooter,date;
        RelativeLayout sender, receiver;
        CircularImageView circularImageView;
CardView cardView;

        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            circularImageView=(CircularImageView)parent.findViewById(R.id.circularImageView1111);
            username=(TextView)parent.findViewById(R.id.text1112);
            message=(TextView)parent.findViewById(R.id.text121);
            date=(TextView)parent.findViewById(R.id.text1211);
            cardView=(CardView)parent.findViewById(R.id.card_view111);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    c=v.getContext();
                    Intent data=new Intent(c,chats.class);
                    Bundle extras=new Bundle();
                    user=FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Chat").child(user.getUid());
                    databaseReference.removeValue();
                    extras.putString("uid",myList.get(i).getUid());
                    extras.putString("username",myList.get(i).getUsername());
                    data.putExtra("extras",extras);
                    c.startActivity(data);
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

    public void UpdateList(ArrayList<RecyclerData5> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}