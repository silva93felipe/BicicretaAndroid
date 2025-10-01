package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Troca;

import java.util.ArrayList;
import java.util.List;

public class TrocaRepository {
    private final String TABELA_PECA = "peca";
    private final String TABELA_TROCA = "troca";
    private BicicretaDbHelper db;
    public TrocaRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(Troca troca){
        SQLiteDatabase con = db.getWritableDatabase();
        con.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put("quilometros_rodados", troca.getQuilometros());
            values.put("data_troca", troca.getData());
            values.put("peca_id", troca.getPecaId());
            con.insert(TABELA_TROCA, null, values);

            values = new ContentValues();
            values.put("quilometros_rodados", 0);
            con.update(TABELA_PECA, values, "id = ?", new String[]{String.valueOf(troca.getPecaId())});

            con.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            con.endTransaction();
        }
    }

    public List<Troca> getAllByPecaId(int pecaId){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Troca> trocas = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT id, quilometros_rodados, peca_id, data_troca FROM "
                + TABELA_TROCA
                + " WHERE peca_id = ? ORDER BY data_troca", new String[]{String.valueOf(pecaId)});
        while(cursor.moveToNext()){
            Troca troca  = new Troca(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
            trocas.add(troca);
        }
        return trocas;
    }

    public void deleteById(int trocaId){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("DELETE FROM " + TABELA_TROCA + " WHERE id = ?",
                new String[]{ String.valueOf(trocaId)});
    }
}
