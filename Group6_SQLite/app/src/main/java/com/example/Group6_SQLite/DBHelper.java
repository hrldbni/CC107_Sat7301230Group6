package com.example.Group6_SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public static String query = "";
    public static String query2 = "";
    private static final String DATABASE_NAME = "DIARY";
    private static final String TABLE_NAME1 = "USER_DATA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "NAME";
    private static final String COL_4 = "DOB";
    private static final String COL_5 = "EMAIL";
    private static final String COL_6 = "PASSWORD";
    private static final String COL_7 = "LASTUPDATE";
    private static final String COL_8 = "CONTENT";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, NAME TEXT, DOB TEXT, EMAIL TEXT, PASSWORD TEXT, LASTUPDATE TEXT, CONTENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public Boolean registerUser(String username, String name, String birthDate, String email, String password, String lastDateUpdate, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, name);
        values.put(COL_4, birthDate);
        values.put(COL_5, email);
        values.put(COL_6, password);
        values.put(COL_7, lastDateUpdate);
        values.put(COL_8, content);


        long result = db.insert(TABLE_NAME1, null, values);
        if (result == -1){
            return false;
        } else
            return true;
    }

    public Boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = {COL_1};
        String selection = COL_2 + "=?" + " and " + COL_6 + "=?";
        String [] selectionargs = {username, password};
        Cursor cursor = db.query(TABLE_NAME1, columns, selection, selectionargs, null, null, null, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if(count > 0)
            return true;
        else {
            return  false;
        }
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean updateData(String userId, String lastDateUpdate, String newcontent)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_7, lastDateUpdate);
        contentValues.put(COL_8, newcontent);

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME1 +" where ID = ? ", new String[]{userId});

        if (cursor.getCount() > 0){

            long result = db.update(TABLE_NAME1, contentValues, " ID = ?", new String[]{userId});

            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {

            return false;
        }

    }

    public boolean deleteData(String userId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME1 +" where ID = ? ", new String[]{userId});

        if (cursor.getCount() > 0){

            long result = db.delete(TABLE_NAME1, " ID = ?", new String[]{userId});

            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {

            return false;
        }



    }


}

