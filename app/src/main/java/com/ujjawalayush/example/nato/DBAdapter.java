package com.ujjawalayush.example.nato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
    static final String KEY_ROWID = "id";
    static final String KEY_CATEGORY = "category";
    static final String KEY_TYPE = "type";
    static final String KEY_ROWID1 = "id1";
    static final String KEY_CATEGORY1 = "category1";
    static final String DATABASE_NAME = "MUSIC";
    static final String DATABASE_TABLE = "music";
    static final String DATABASE_TABLE1 = "music1";
    static final String KEY_ROWID2 = "id2";
    static final String KEY_ROWID3 = "id3";
    static final String KEY_TYPE3 = "type3";
    static final String KEY_USERNAME3 = "username3";
    static final String KEY_UID3 = "uid3";
    static final String KEY_STATUS3 = "status3";
    static final String KEY_USERNAME2 = "username2";
    static final String KEY_UID2 = "uid2";
    static final String KEY_STATUS2 = "status2";
    static final String KEY_TYPE2 = "type2";
    static final String KEY_DATE3 = "date3";
    static final String KEY_DATE2 = "date2";
    static final String KEY_ROW="id4";
    static final String KEY_TYPES = "types";
    static final String KEY_ADD = "add4";
    static final String KEY_EXPECTED = "expected";
    static final String KEY_STATUS = "status";
    static final String KEY_DATE = "date";
    static final String KEY_MEMBER = "member";
    static final String KEY_NAME = "name";
    static final String KEY_ROW1="id5";
    static final String KEY_TYPESQ="id523321232";

    static final String KEY_U = "u1";
    static final String KEY_ROW2="id7";
    static final String KEY_U2 = "u2";
    static final String KEY_TRIP = "trip1";
    static final String KEY_MESSAGE = "message11";
    static final String KEY_USER = "user1111";
    static final String KEY_ROW11="id51";
    static final String KEY_U1 = "u11";
    static final String KEY_U12 = "u112";
    static final String KEY_Q = "u1121";
    static final String KEY_TRIP1= "trip11";
    static final String KEY_MESSAGE1 = "message111";
    static final String KEY_USER1 = "user11111";
    static final String DATABASE_TABLE2 = "music2";
    static final String DATABASE_TABLE3 = "music3";
    static final String DATABASE_TABLE4 = "music4";
    static final String DATABASE_TABLE5 = "music5";
    static final String DATABASE_TABLE6 = "music6";
    static final String DATABASE_TABLE7 = "music7";
    static final String DATABASE_TABLE8 = "music8";
    static final String KEY_TRIPNAME = "user11111";
    static final String KEY_TIME = "user1111112";

    static final String KEY_ROWTRIP="id511";
    static final String KEY_MEMBERTRIP = "u111";
    static final String KEY_MEMBERNAME = "u111123322";

    static final String TAG = "DBAdapter";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement,"+KEY_CATEGORY+" text not null,"+KEY_TYPE+" text not null);";
    static final String DATABASE_CREATE1 = "create table "+DATABASE_TABLE1+" ("+KEY_ROWID1+" integer primary key autoincrement,"+KEY_CATEGORY1+" text not null);";
    static final String DATABASE_CREATE2 = "create table "+DATABASE_TABLE2+" ("+KEY_ROWID2+" integer primary key autoincrement,"+KEY_TYPE2+" text not null,"+KEY_DATE2+" text not null,"+KEY_USERNAME2+" text not null,"+KEY_UID2+" text not null,"+KEY_STATUS2+" text not null);";
    static final String DATABASE_CREATE3 = "create table "+DATABASE_TABLE3+" ("+KEY_ROWID3+" integer primary key autoincrement,"+KEY_TYPE3+" text not null,"+KEY_DATE3+" text not null,"+KEY_USERNAME3+" text not null,"+KEY_UID3+" text not null,"+KEY_STATUS3+" text not null);";
    static final String DATABASE_CREATE4 = "create table "+DATABASE_TABLE4+" ("+KEY_ROW+" integer primary key autoincrement,"+KEY_TYPES+" text not null,"+KEY_DATE+" text not null,"+KEY_NAME+" text not null,"+KEY_ADD+" text not null,"+KEY_STATUS+" text not null,"+KEY_EXPECTED+" text not null,"+KEY_MEMBER+" text not null);";
    static final String DATABASE_CREATE5 = "create table "+DATABASE_TABLE5+" ("+KEY_ROW1+" integer primary key autoincrement,"+KEY_U+" text not null,"+KEY_TRIP+" text not null,"+KEY_USER+" text not null,"+KEY_MESSAGE+" text not null,"+KEY_TIME+" text not null);";
    static final String DATABASE_CREATE6 = "create table "+DATABASE_TABLE6+" ("+KEY_ROW11+" integer primary key autoincrement,"+KEY_U1+" text not null,"+KEY_TRIP1+" text not null,"+KEY_USER1+" text not null,"+KEY_MESSAGE1+" text not null);";
    static final String DATABASE_CREATE7 = "create table "+DATABASE_TABLE7+" ("+KEY_ROW2+" integer primary key autoincrement,"+KEY_U2+" text not null,"+KEY_U12+" text not null,"+KEY_Q+" text not null);";
    static final String DATABASE_CREATE8 = "create table "+DATABASE_TABLE8+" ("+KEY_ROWTRIP+" integer primary key autoincrement,"+KEY_TRIPNAME+" text not null,"+KEY_MEMBERTRIP+" text not null,"+KEY_MEMBERNAME+" text not null,"+KEY_TYPESQ+" text not null);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE1);
            db.execSQL(DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE3);
            db.execSQL(DATABASE_CREATE4);
            db.execSQL(DATABASE_CREATE5);
            db.execSQL(DATABASE_CREATE6);
            db.execSQL(DATABASE_CREATE7);
            db.execSQL(DATABASE_CREATE8);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE1");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE2");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE3");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE4");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE5");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE6");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE7");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE8");

            onCreate(db);
        }
    }
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    public long insertContact(String email, String password)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY,email);
        initialValues.put(KEY_TYPE,password);
        return db.insert(DATABASE_TABLE, null,initialValues);
    }
    public long insertMember(String email, String password,String pssword,String type)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TRIPNAME,email);
        initialValues.put(KEY_MEMBERTRIP,password);
        initialValues.put(KEY_MEMBERNAME,pssword);
        initialValues.put(KEY_TYPESQ,type);

        return db.insert(DATABASE_TABLE8, null,initialValues);
    }
    public long insertX(String email,String p,String q)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_U2,email);
        initialValues.put(KEY_U12,p);
        initialValues.put(KEY_Q,q);

        return db.insert(DATABASE_TABLE7, null,initialValues);
    }
    public long insertContact1(String email)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY1,email);
        return db.insert(DATABASE_TABLE1, null,initialValues);
    }
    public long insertContact3(String message,String date,String username, String uid,String status)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE3,message);
        initialValues.put(KEY_DATE3,date);
        initialValues.put(KEY_USERNAME3,username);
        initialValues.put(KEY_UID3,uid);
        initialValues.put(KEY_STATUS3,status);


        return db.insert(DATABASE_TABLE3, null,initialValues);
    }
    public long insertNotifications(String uid,String trip,String username, String message,String time)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_U,uid);
        initialValues.put(KEY_TRIP,trip);
        initialValues.put(KEY_USER,username);
        initialValues.put(KEY_MESSAGE,message);
        initialValues.put(KEY_TIME,time);


        return db.insert(DATABASE_TABLE5, null,initialValues);
    }
    public long insertNotification(String uid,String trip,String username, String message)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_U1,uid);
        initialValues.put(KEY_TRIP1,trip);
        initialValues.put(KEY_USER1,username);
        initialValues.put(KEY_MESSAGE1,message);


        return db.insert(DATABASE_TABLE6, null,initialValues);
    }
    public long insertContact2(String message,String date,String username, String uid,String status)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE2,message);
        initialValues.put(KEY_DATE2,date);
        initialValues.put(KEY_USERNAME2,username);
        initialValues.put(KEY_UID2,uid);
        initialValues.put(KEY_STATUS2,status);

        return db.insert(DATABASE_TABLE2, null,initialValues);
    }
    public long insertTrip(String type,String date,String name, String add,String status,String expected,String member)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPES,type);
        initialValues.put(KEY_DATE,date);
        initialValues.put(KEY_NAME,name);
        initialValues.put(KEY_ADD,add);
        initialValues.put(KEY_STATUS,status);
        initialValues.put(KEY_EXPECTED,expected);
        initialValues.put(KEY_MEMBER,member);
        return db.insert(DATABASE_TABLE4, null,initialValues);
    }
    //---deletes a particular title---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }
    public boolean deleteMember(long rowId)
    {
        return db.delete(DATABASE_TABLE8, KEY_ROWTRIP +
                "=" + rowId, null) > 0;
    }
    public boolean deleteContact1(long rowId)
    {
        return db.delete(DATABASE_TABLE1, KEY_ROWID1 +
                "=" + rowId, null) > 0;
    }
    //---deletes a particular title---
    public boolean deleteContact3(long rowId)
    {
        return db.delete(DATABASE_TABLE3, KEY_ROWID3 +
                "=" + rowId, null) > 0;
    }
    public boolean deleteContact2(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_ROWID2 +
                "=" + rowId, null) > 0;
    }
    public boolean deleteTrip(long rowId)
    {
        return db.delete(DATABASE_TABLE4, KEY_ROW +
                "=" + rowId, null) > 0;
    }
    public boolean deleteNotifications(long rowId)
    {
        return db.delete(DATABASE_TABLE5, KEY_ROW1 +
                "=" + rowId, null) > 0;
    }
    public boolean deleteNotification(long rowId)
    {
        return db.delete(DATABASE_TABLE6, KEY_ROW11 +
                "=" + rowId, null) > 0;
    }
    public boolean deleteAllContacts()
    {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }
    public boolean deleteAllContacts1()
    {
        return db.delete(DATABASE_TABLE1, null, null) > 0;
    }
    public boolean deleteAllContacts2()
    {
        return db.delete(DATABASE_TABLE2, null, null) > 0;
    }
    public boolean deleteAllContacts3()
    {
        return db.delete(DATABASE_TABLE3, null, null) > 0;
    }
    public boolean deleteAllTrips()
    {
        return db.delete(DATABASE_TABLE4, null, null) > 0;
    }
    public boolean deleteAllNotifications()
    {
        return db.delete(DATABASE_TABLE5, null, null) > 0;
    }
    public boolean deleteAllNotification()
    {
        return db.delete(DATABASE_TABLE6, null, null) > 0;
    }
    public boolean deleteAllMembers()
    {
        return db.delete(DATABASE_TABLE8, null, null) > 0;
    }
    public boolean deleteAllX()
    {
        return db.delete(DATABASE_TABLE7, null, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_CATEGORY,
                        KEY_TYPE},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllContacts1()
    {
        return db.query(DATABASE_TABLE1, new String[] {
                        KEY_ROWID1,
                        KEY_CATEGORY1},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllMembers()
    {
        return db.query(DATABASE_TABLE8, new String[] {
                        KEY_ROWTRIP,
                        KEY_TRIPNAME,KEY_MEMBERTRIP,KEY_MEMBERNAME,KEY_TYPESQ},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllContacts3()
    {
        return db.query(DATABASE_TABLE3, new String[] {
                        KEY_ROWID3,
                        KEY_TYPE3,KEY_DATE3,KEY_USERNAME3,KEY_UID3,KEY_STATUS3},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllX()
    {
        return db.query(DATABASE_TABLE7, new String[] {
                        KEY_ROW2,
                        KEY_U2,KEY_U12,KEY_Q},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllContacts2()
    {
        return db.query(DATABASE_TABLE2, new String[] {
                        KEY_ROWID2,
                        KEY_TYPE2,KEY_DATE2,KEY_USERNAME2,KEY_UID2,KEY_STATUS2},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllTrips()
    {
        return db.query(DATABASE_TABLE4, new String[] {
                        KEY_ROW,
                        KEY_TYPES,KEY_DATE,KEY_NAME,KEY_ADD,KEY_STATUS,KEY_EXPECTED,KEY_MEMBER},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllNotifications()
    {
        return db.query(DATABASE_TABLE5, new String[] {
                        KEY_ROW1,
                        KEY_U,KEY_TRIP,KEY_USER,KEY_MESSAGE,KEY_TIME},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllNotification()
    {
        return db.query(DATABASE_TABLE6, new String[] {
                        KEY_ROW11,
                        KEY_U1,KEY_TRIP1,KEY_USER1,KEY_MESSAGE1},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getTrip(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE4, new String[] {
                        KEY_ROW,
                        KEY_TYPES,KEY_DATE,KEY_NAME,KEY_ADD,KEY_STATUS2,KEY_EXPECTED,KEY_MEMBER},
                KEY_ROW + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    //---retrieves a particular title---
    public Cursor getContact(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_CATEGORY,
                        KEY_TYPE},
                KEY_ROWID + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public Cursor getMember(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE8, new String[] {
                        KEY_ROWTRIP,
                        KEY_TRIPNAME,
                        KEY_MEMBERTRIP,KEY_MEMBERNAME,KEY_TYPESQ},
                KEY_ROWTRIP + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public Cursor getContact1(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE1, new String[] {
                        KEY_ROWID1,
                        KEY_CATEGORY1},
                KEY_ROWID1 + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public Cursor getNotifications(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE5, new String[] {
                        KEY_ROW1,
                        KEY_U,KEY_TRIP,KEY_USER,KEY_MESSAGE,KEY_TIME},
                KEY_ROW1 + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public Cursor getNotification(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE6, new String[] {
                        KEY_ROW11,
                        KEY_U1,KEY_TRIP1,KEY_USER1,KEY_MESSAGE1},
                KEY_ROW11 + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public Cursor getContac3(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE3, new String[] {
                        KEY_ROWID3,
                        KEY_TYPE3,KEY_DATE3,KEY_USERNAME3,KEY_UID3,KEY_STATUS3},
                KEY_ROWID3 + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public Cursor getContac2(long rowId) throws SQLException
    {
        return db.query(true, DATABASE_TABLE2, new String[] {
                        KEY_ROWID2,
                        KEY_TYPE2,KEY_DATE2,KEY_USERNAME2,KEY_UID2,KEY_STATUS2},
                KEY_ROWID2 + "=?",
                new String[]{String.valueOf(rowId)},
                null,
                null,
                null,
                null);
    }
    public boolean updateContact(long rowId, String name, String model){
        ContentValues args = new ContentValues();
        args.put(KEY_CATEGORY, name);
        args.put(KEY_TYPE, model);

        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) >0;
    }
    public boolean updateContact1(long rowId, String name){
        ContentValues args = new ContentValues();
        args.put(KEY_CATEGORY1, name);

        return db.update(DATABASE_TABLE1, args, KEY_ROWID1 + "=" + rowId, null) >0;
    }
    public boolean updateContact3(long rowId,String message,String date,String username, String uid,String status){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE3,message);
        initialValues.put(KEY_DATE3,date);
        initialValues.put(KEY_USERNAME3,username);
        initialValues.put(KEY_UID3,uid);
        initialValues.put(KEY_STATUS3,status);

        return db.update(DATABASE_TABLE3, initialValues, KEY_ROWID3 + "=" + rowId, null) >0;
    }
    public boolean updateContact2(long rowId,String message,String date,String username, String uid,String status){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE2,message);
        initialValues.put(KEY_DATE2,date);
        initialValues.put(KEY_USERNAME2,username);
        initialValues.put(KEY_UID2,uid);
        initialValues.put(KEY_STATUS2,status);
        return db.update(DATABASE_TABLE2, initialValues, KEY_ROWID2 + "=" + rowId, null) >0;
    }
    public boolean updateTrip(long rowId,String type,String date,String name, String add,String status,String expected,String member){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPES,type);
        initialValues.put(KEY_DATE,date);
        initialValues.put(KEY_NAME,name);
        initialValues.put(KEY_ADD,add);
        initialValues.put(KEY_STATUS,status);
        initialValues.put(KEY_EXPECTED,expected);
        initialValues.put(KEY_MEMBER,member);
        return db.update(DATABASE_TABLE2, initialValues, KEY_ROWID2 + "=" + rowId, null) >0;
    }
    public boolean updateNotifications(long rowId,String uid,String trip,String username, String message,String time){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_U,uid);
        initialValues.put(KEY_TRIP,trip);
        initialValues.put(KEY_USER,username);
        initialValues.put(KEY_MESSAGE,message);
        initialValues.put(KEY_TIME,time);

        return db.update(DATABASE_TABLE5, initialValues, KEY_ROW1 + "=" + rowId, null) >0;
    }
    public boolean updateNotification(long rowId,String uid,String trip,String username, String message){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_U1,uid);
        initialValues.put(KEY_TRIP1,trip);
        initialValues.put(KEY_USER1,username);
        initialValues.put(KEY_MESSAGE1,message);
        return db.update(DATABASE_TABLE6, initialValues, KEY_ROW11 + "=" + rowId, null) >0;
    }
}