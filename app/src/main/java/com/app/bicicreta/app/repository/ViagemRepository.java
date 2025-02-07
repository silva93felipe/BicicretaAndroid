package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Viagem;

import java.util.ArrayList;
import java.util.List;

public class ViagemRepository {
    private final String TABLE_VIAGEM = "viagem";
    private final String TABEL_BICICLETA = "bicicleta";
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

    public List<Viagem> getLastByParam(int quantidade){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT * FROM " + TABLE_VIAGEM + " ORDER BY data_viagem LIMIT ?;", new String[]{ String.valueOf(quantidade) });
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(3), cursor.getInt(2),  cursor.getString(1));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAll(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT id, destino, quilometros_rodados, data_viagem, bicicleta_id  FROM " + TABLE_VIAGEM + " INNER JOIN " + TABEL_BICICLETA + " ORDER BY data_viagem;", null);
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(3), cursor.getInt(2),  cursor.getString(1));
            viagens.add(viagem);
        }
        return viagens;
    }
}
