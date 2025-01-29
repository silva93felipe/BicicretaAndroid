package com.app.bicicreta.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BicicretaDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "bicreta.db";
    private static final int DATABASE_VERSION = 1;
    public BicicretaDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableUser());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String sqlCreateTableUser(){
        return "CREATE TABLE user ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT );";
    }

    public void insert(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.insert(tableName, null, values);
    }
}
