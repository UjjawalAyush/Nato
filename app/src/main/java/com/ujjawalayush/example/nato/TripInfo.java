package com.ujjawalayush.example.nato;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TripInfo extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    String username,uid,message,trip;
    RecyclerView recyclerView,recyclerView1;
    FirebaseUser user;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    ArrayList<RecyclerData> myList=new ArrayList<>();
    RecyclerAdapter9 mAdapter;
    String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tripinfo);
        imageView=(ImageView)findViewById(R.id.u1);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView3);
        recyclerView1=(RecyclerView)findViewById(R.id.recyclerView4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager);
        mAdapter=new RecyclerAdapter9(myList);
        recyclerView1.setAdapter(mAdapter);
        textView=(TextView)findViewById(R.id.q1);
        toolbar=(Toolbar)findViewById(R.id.toolb8);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading! Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Intent data=getIntent();
        username=data.getBundleExtra("extras").getString("username");
        uid=data.getBundleExtra("extras").getString("uid");
        message=data.getBundleExtra("extras").getString("message");
        trip=data.getBundleExtra("extras").getString("trip");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("photo")){
                    Picasso.get().load(dataSnapshot.child("photo").getValue().toString()).centerCrop().fit().into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DBAdapter db=new DBAdapter(this);
        db.open();
        Cursor c=db.getAllTrips();
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(3).equals(trip)){
                type=c.getString(1);
                break;
            }
            c.moveToNext();
        }
        toolbar.setTitle(trip);
        user=FirebaseAuth.getInstance().getCurrentUser();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("members");
        Query query=databaseReference1.orderByKey().equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    textView.setText("Successfully joined");
                    progressDialog.dismiss();
                }
                else{
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    RecyclerData mLog = new RecyclerData();
                    mLog.setDisplay(dataSnapshot1.getValue().toString());
                    mLog.setValue(dataSnapshot1.getKey());
                    myList.add(mLog);
                    mAdapter.notifyData(myList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onClick(View v){
        if(textView.getText().toString().equals("Join Group")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog);
            builder.setTitle("Do you wish to Join?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progressDialog.setMessage("Loading! Please Wait...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    myList.clear();
                    mAdapter.notifyData(myList);
                    final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("Details");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Invitations").child(trip).child(user.getUid()).child(uid);
                            databaseReference1.removeValue();
                            DatabaseReference databaseReference11 = FirebaseDatabase.getInstance().getReference().child("Invitations").child(trip).child(uid).child(user.getUid());
                            databaseReference11.child("Status").setValue("Accepted");
                            databaseReference11.child("Username").setValue(user.getDisplayName());
                            databaseReference11.child("Trip").setValue(trip);
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            final String formattedDate = df.format(c);
                            Calendar cal = Calendar.getInstance();
                            Date date=cal.getTime();
                            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                            final String formattedTime=dateFormat.format(date);
                            DatabaseReference databaseReference12 = FirebaseDatabase.getInstance().getReference().child("Trips").child(trip).child("Notifications");
                            final String k = databaseReference12.push().getKey();
                            databaseReference12.child(k).child("Username").setValue(user.getDisplayName());
                            databaseReference12.child(k).child("Uid").setValue(user.getUid());
                            databaseReference12.child(k).child("Message").setValue("joined");
                            databaseReference12.child(k).child("type").setValue(type);
                            databaseReference12.child(k).child("date").setValue(formattedDate+" "+formattedTime);
                            textView.setText("Successfully Joined");
                            DatabaseReference databaseReference13 = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Trips");
                            databaseReference13.child(trip).setValue("Yes");
                            DatabaseReference databaseReference131 = FirebaseDatabase.getInstance().getReference();
                            progressDialog.dismiss();
                            databaseReference131.child("Trips").child(trip).child("members").child(user.getUid()).setValue(user.getDisplayName());
                            String a=dataSnapshot.child("members").getValue().toString();
                            long b=Long.parseLong(a);
                            b++;
                            a=Long.toString(b);
                            Intent data = new Intent(TripInfo.this, Trip.class);
                            data.putExtra("trip", trip);
                            databaseReference.child("members").setValue(a);
                            startActivity(data);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Invitations").child(trip).child(user.getUid()).child(uid);
                    databaseReference1.removeValue();
                    DatabaseReference databaseReference11 = FirebaseDatabase.getInstance().getReference().child("Invitations").child(trip).child(uid).child(user.getUid());
                    databaseReference11.setValue("Rejected");
                    textView.setText("Not a member");
                }
            });
            builder.show();
        }
        else{
            Toast.makeText(this,"Already responded",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(progressDialog.isShowing()){
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.dismiss();
        }
        Intent data=new Intent(TripInfo.this,Notifications.class);
        startActivity(data);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
