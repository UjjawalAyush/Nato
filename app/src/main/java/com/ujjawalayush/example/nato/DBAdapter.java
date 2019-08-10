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
    static final String KEY_DIFFICULTY = "difficulty";
    static final String KEY_QUESTION = "question";
    static final String KEY_CORRECT = "correct";
    static final String KEY_EMAIL = "email";
    static final String DATABASE_NAME = "FRIENDS";
    static final String DATABASE_TABLE = "friends";
    static final String TAG = "DBAdapter";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement,"+KEY_CATEGORY+" text not null,"+KEY_DIFFICULTY+" text not null,"+KEY_QUESTION+" text not null,"+KEY_CORRECT+" BLOB,"+KEY_EMAIL+" text not null);";
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
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
    public long insertContact(String uid,String displayName, String phoneNumber,byte[] photoUrl,String email)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY,uid);
        initialValues.put(KEY_EMAIL,email);
        initialValues.put(KEY_DIFFICULTY,displayName);
        initialValues.put(KEY_QUESTION,phoneNumber);
        initialValues.put(KEY_CORRECT,photoUrl);
        return db.insert(DATABASE_TABLE, null,initialValues);
    }

    //---deletes a particular title---
    public boolean deleteContact(long rowId)
    {
        db.execSQL("delete from "+ DATABASE_TABLE);
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }
    public boolean deleteAllContacts()
    {
        return db.delete(DATABASE_TABLE,null, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_CATEGORY,
                        KEY_EMAIL,
                        KEY_DIFFICULTY,
                        KEY_QUESTION,KEY_CORRECT},
                null,
                null,
                null,
                null,
                null);
    }

    //---retrieves a particular title---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor c= db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_CATEGORY,
                        KEY_EMAIL,
                        KEY_DIFFICULTY,
                        KEY_QUESTION,KEY_CORRECT},
                KEY_ROWID + " = ?",
                new String[]{Long.toString(rowId)},
                null,
                null,
                null,
                null);
        if(c !=null)
        {
            c.moveToFirst();
        }
        return c;
    }
    public boolean updateContact(Long id, String uid, String username,String phone,byte[] profile,String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY,uid);
        initialValues.put(KEY_DIFFICULTY,username);
        initialValues.put(KEY_QUESTION,phone);
        initialValues.put(KEY_CORRECT,profile);
        initialValues.put(KEY_EMAIL,email);

        return db.update(DATABASE_TABLE, initialValues, KEY_ROWID +" = ? ", new String[]{Long.toString(id)})>0;
    }
}

