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
public class RecyclerAdapter7 extends RecyclerView.Adapter<RecyclerAdapter7.RecyclerItemViewHolder> {
    public ArrayList<RecyclerData8> myList;
    int mLastPosition = 0, request_code = 1;
    long id2;
    Context c;
    String m;
    int i;
    FirebaseUser user;
    String a,b,d,e,f,g,h;
    public RecyclerAdapter7(ArrayList<RecyclerData8> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row6, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.value.setText(myList.get(position).getUsername());
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }
    public void notifyData(ArrayList<RecyclerData8> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title, description, date;
        TextView value, display;
        CircularImageView edit;
        ImageView person;
        ImageButton imageButton;
        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            user=FirebaseAuth.getInstance().getCurrentUser();
            value=(TextView)parent.findViewById(R.id.user1);
            imageButton=(ImageButton)parent.findViewById(R.id.user3);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=getAdapterPosition();
                    c=v.getContext();
                    DBAdapter db=new DBAdapter(c);
                    db.open();
                    Cursor d=db.getAllContacts();
                    d.moveToFirst();
                    while(true){
                        if(d.getString(1).equals(myList.get(i).getNew())){
                            db.deleteContact(Long.parseLong(d.getString(0)));
                            db.close();
                            break;
                        }
                        d.moveToNext();
                    }
                    Toast.makeText(c,"Person successfully removed",Toast.LENGTH_SHORT).show();
                    Intent data=new Intent(c,tag.class);
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
    public void UpdateList(ArrayList<RecyclerData8> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}