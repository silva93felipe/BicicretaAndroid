package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.User;

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

    public void update(User user){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABLE_USER + " SET  nome = ? WHERE id = ? ",  new String[]{user.getNome() ,String.valueOf(user.getId())});
    }

    public User getOne(){
        SQLiteDatabase con = db.getWritableDatabase();
        Cursor cursor = con.rawQuery("SELECT id, nome FROM " + TABLE_USER + " LIMIT 1;", null);
        while(cursor.moveToNext()){
            User user = new User(cursor.getInt(0), cursor.getString(1));
            return user;
        }
        return null;
    }
}
