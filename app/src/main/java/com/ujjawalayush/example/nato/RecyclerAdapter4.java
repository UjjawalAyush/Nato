package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class RecyclerAdapter4 extends RecyclerView.Adapter<RecyclerAdapter4.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData4> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;


    public RecyclerAdapter4(ArrayList<RecyclerData4> myList) {
        this.myList = myList;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row3, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        if(myList.get(position).getStatus().equals("1")){
            holder.senderFooter.setVisibility(View.INVISIBLE);
            holder.senderText.setVisibility(View.INVISIBLE);
            holder.sender.setVisibility(View.INVISIBLE);
            holder.receiverText.setVisibility(View.VISIBLE);
            holder.receiverText.setVisibility(View.VISIBLE);
            holder.receiver.setVisibility(View.VISIBLE);
            holder.receiverText.setText(myList.get(position).getMessage());
            holder.receiverFooter.setText(myList.get(position).getDate());
        }
        else{
            holder.senderFooter.setVisibility(View.VISIBLE);
            holder.senderText.setVisibility(View.VISIBLE);
            holder.sender.setVisibility(View.VISIBLE);
            holder.receiverText.setVisibility(View.INVISIBLE);
            holder.receiverText.setVisibility(View.INVISIBLE);
            holder.receiver.setVisibility(View.INVISIBLE);
            holder.senderText.setText(myList.get(position).getMessage());
            holder.senderFooter.setText(myList.get(position).getDate());
        }

    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<RecyclerData4> myList,int x) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        if(x==1){
        }
        notifyDataSetChanged();

    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        public String title, description, date;
        TextView senderText, senderHeader, senderFooter, receiverText, receiverFooter;
        RelativeLayout sender, receiver;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            senderText = (TextView) parent.findViewById(R.id.senderText);
            senderFooter = (TextView) parent.findViewById(R.id.senderFooter);
            receiverFooter = (TextView) parent.findViewById(R.id.recieverFooter);
            receiverText = (TextView) parent.findViewById(R.id.recieverText);
            sender = (RelativeLayout) parent.findViewById(R.id.sender);
            receiver = (RelativeLayout) parent.findViewById(R.id.receiver);
            receiver.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    i=getAdapterPosition();
                    c=v.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Delete Message");
                    builder.setPositiveButton("delete for me", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBAdapter db=new DBAdapter(c);
                            db.open();
                                db.deleteContact3(myList.get(i).getId());
                                myList.remove(i);
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
                    return true;
                }
            });
            sender.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    i=getAdapterPosition();
                    c=v.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Delete Message");
                    builder.setPositiveButton("delete for me", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBAdapter db=new DBAdapter(c);
                            db.open();
                                db.deleteContact3(myList.get(i).getId());
                                myList.remove(i);
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
                    return true;
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

    public void UpdateList(ArrayList<RecyclerData4> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}

