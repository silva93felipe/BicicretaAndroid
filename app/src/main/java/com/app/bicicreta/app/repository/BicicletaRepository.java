package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Bicicleta;

public class BicicletaRepository {
    private final String TABEL_BICICLETA = "bicicleta";
    BicicretaDbHelper db;
    public BicicletaRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(Bicicleta bicicleta){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", bicicleta.getModelo());
        values.put("aro", bicicleta.getAro());
        values.put("tamanho_quadro", bicicleta.getTamanhoQuadro());
        values.put("quilometros_rodados", bicicleta.getQuilometrosRodados());
        values.put("quantidade_marchas", bicicleta.getQuantidadeMarchas());
        con.insert(TABEL_BICICLETA, null, values);
    }

    public Cursor getById(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        return con.rawQuery("SELECT * FROM " + TABEL_BICICLETA + " WHERE id = ?", new String[]{ String.valueOf(id)});
    }

    public Cursor getAll(){
        SQLiteDatabase con = db.getWritableDatabase();
        return con.rawQuery("SELECT * FROM " + TABEL_BICICLETA, null);
    }
}
