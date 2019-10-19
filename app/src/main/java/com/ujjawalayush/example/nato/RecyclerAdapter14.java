package com.ujjawalayush.example.nato;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class RecyclerAdapter14 extends RecyclerView.Adapter<RecyclerAdapter14.RecyclerItemViewHolder> {
    public ArrayList<RecyclerData14> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;
    public RecyclerAdapter14(ArrayList<RecyclerData14> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row14, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.title.setText(myList.get(position).getTime());
        holder.comment.setText(myList.get(position).getEditText());
        holder.name.setText("By : "+myList.get(position).getUsername());
        Picasso.get().load(myList.get(position).getPictures()).centerCrop().fit().into(holder.person);
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }
    public void notifyData(ArrayList<RecyclerData14> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String  description, date;
        TextView name,comment,title;
        CircularImageView edit;
        ImageView person;
        ImageButton imageButton;
        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            person=(ImageView)parent.findViewById(R.id.imageView5);
            name=(TextView)parent.findViewById(R.id.name);
            comment=(TextView)parent.findViewById(R.id.comment);
            title=(TextView)parent.findViewById(R.id.time);

        }
    }
}