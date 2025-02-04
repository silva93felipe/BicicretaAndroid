package com.app.bicicreta.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BicicretaDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bicreta.db";
    private static final int DATABASE_VERSION = 1;
    public BicicretaDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreteTables());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private String sqlCreteTables(){
        String sql = "CREATE TABLE bicicleta ( id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(40), valor REAL DEFAULT 0, quilometros_rodados INTEGER DEFAULT 0 ); ";
        sql += "CREATE TABLE peca ( id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(50), valor REAL DEFAULT 0.0, quilometros_rodados INTEGER DEFAULT 0, data_compra DEFAULT CURRENT_TIMESTAMP, bike_id INTEGER, FOREIGN KEY (bike_id) REFERENCES bicicleta (id) ); ";
        sql += "CREATE TABLE viagem ( id INTEGER PRIMARY KEY AUTOINCREMENT, local VARCHAR(40), quilometros_rodados INTEGER DEFAULT 0, data_viagem DEFAULT CURRENT_TIMESTAMP, bike_id INTEGER, FOREIGN KEY (bike_id) REFERENCES bicicleta (id) ); ";
        sql += "CREATE TABLE user ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT ); ";
        return sql;
    }
}
