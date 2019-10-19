package com.ujjawalayush.example.nato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerItemViewHolder> {

    private ArrayList<RecyclerData> myList;
    int mLastPosition=0,request_code=1;
    long id2;
    Context c;
    String m;
    int i;
    public RecyclerAdapter(ArrayList<RecyclerData> myList) {
        this.myList = myList;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHolder ", myList.size() + "");
        holder.display.setText(myList.get(position).getDiplay());
        holder.value.setText(myList.get(position).getValue());

    }

    @Override
    public int getItemCount() {
        return (null != myList?myList.size():0);
    }
    public void notifyData(ArrayList<RecyclerData> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        public String title, description, date;
        TextView value,display;
        Button edit;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            display=(TextView)parent.findViewById(R.id.display);
            value=(TextView)parent.findViewById(R.id.value);
            edit=(Button)parent.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c=v.getContext();
                    i=getAdapterPosition();
                    Intent data=new Intent(c,Setting.class);
                    data.putExtra("display",myList.get(i).getDiplay());
                    c.startActivity(data);
                }
            });
        }
    }
    public void UpdateList(ArrayList<RecyclerData> newList){
        myList = new ArrayList<>();
        myList.addAll(newList);
        notifyDataSetChanged();
    }
}

