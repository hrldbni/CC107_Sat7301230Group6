package com.example.deardearyjaycabasag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static String query = "";
    public static String query2 = "";
    private static final String DATABASE_NAME = "DEAR_DIARY";
    private static final String TABLE_NAME1 = "USER_DATA";
    private static final String TABLE_NAME2 = "DIARY_DATA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "NAME";
    private static final String COL_4 = "DOB";
    private static final String COL_5 = "EMAIL";
    private static final String COL_6 = "PASSWORD";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, NAME TEXT, DOB TEXT, EMAIL TEXT, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + "(DIARYID INTEGER, LASTUPDATE TEXT, CONTENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public Boolean registerUser(String username, String name, String birthDate, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, name);
        values.put(COL_4, birthDate);
        values.put(COL_5, email);
        values.put(COL_6, password);

        long result = db.insert(TABLE_NAME1, null, values);
        if (result == -1){
            return false;
        } else
            return true;
    }

    public Boolean registerDiary(Integer diaryId, String updateDate, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, diaryId);
        values.put(COL_3, updateDate);
        values.put(COL_4, content);

        long result = db.insert(TABLE_NAME2, null, values);
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

    public Cursor getDiaryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query2, null);
        return cursor;
    }
}

