package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;

public class UserRepository {
    private final String TABLE_USER = "user";
    BicicretaDbHelper db;
    public UserRepository(Context context){
        db = new BicicretaDbHelper(context);
    }
    public void add(String name){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", name);
        con.insert(TABLE_USER, null, values);
    }

    public Cursor getOne(){
        SQLiteDatabase con = db.getWritableDatabase();
        return con.rawQuery("SELECT name FROM " + TABLE_USER + " LIMIT 1;", null);
    }
}
