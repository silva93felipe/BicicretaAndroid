package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Viagem;

public class ViagemRepository {
    private final String TABLE_VIAGEM = "viagem";
    BicicretaDbHelper db;
    public ViagemRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(Viagem viagem){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("destino", viagem.getDestino());
        values.put("quilometros_rodados", viagem.getQuilometros());
        values.put("data_viagem", viagem.getData());
        values.put("bicicleta_id", viagem.getBicicletaId());
        con.insert(TABLE_VIAGEM, null, values);
    }

    public Cursor getLastByParam(int quantidade){
        SQLiteDatabase con = db.getWritableDatabase();
        return con.rawQuery("SELECT * FROM " + TABLE_VIAGEM + " ORDER BY data_viagem LIMIT ?;", new String[]{ String.valueOf(quantidade) });
    }

    public Cursor getAll(){
        SQLiteDatabase con = db.getWritableDatabase();
        return con.rawQuery("SELECT * FROM " + TABLE_VIAGEM + " ORDER BY data_viagem;", null);
    }
}
