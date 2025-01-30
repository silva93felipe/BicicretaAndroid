package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;

public class UserRepository {
    private final String TABLE_NAME = "user";
    BicicretaDbHelper db;
    public UserRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(String name){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        con.insert(TABLE_NAME, null, values);
    }

    public void getFirst(){

    }

}
