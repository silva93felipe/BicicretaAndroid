package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Bicicleta;

import java.util.ArrayList;
import java.util.List;

public class BicicletaRepository {
    private final String TABEL_BICICLETA = "bicicleta";
    BicicretaDbHelper db;
    public BicicletaRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(Bicicleta bicicleta){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("modelo", bicicleta.getModelo());
        values.put("tamanho_aro", bicicleta.getAro());
        values.put("tamanho_quadro", bicicleta.getTamanhoQuadro());
        values.put("quilometros_rodados", bicicleta.getQuilometrosRodados());
        values.put("quantidade_marchas", bicicleta.getQuantidadeMarchas());
        values.put("observacao", bicicleta.getObservacao());
        con.insert(TABEL_BICICLETA, null, values);
    }

    public void updateQuilometrosRodadosByBicicletaId(int bicicletaId, int quilometros){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABEL_BICICLETA + " SET quilometros_rodados = quilometros_rodados + ? WHERE id = ?", new String[]{ String.valueOf(quilometros),  String.valueOf(bicicletaId)});
    }

    public Bicicleta getById(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        Cursor cursor = con.rawQuery("SELECT id, modelo, tamanho_aro, quantidade_marchas, tamanho_quadro, quilometros_rodados, observacao  FROM " + TABEL_BICICLETA + " WHERE id = ?", new String[]{ String.valueOf(id)});
        while(cursor.moveToNext()){
            return new Bicicleta(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
        }
        return null;
    }

    public void update(Bicicleta bicicleta){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABEL_BICICLETA + " SET modelo = ?, tamanho_aro = ?, quantidade_marchas = ?, tamanho_quadro = ?, quilometros_rodados = ?, observacao = ? WHERE id = ?",
                new String[]{ String.valueOf(bicicleta.getModelo()), String.valueOf(bicicleta.getAro()), String.valueOf(bicicleta.getQuantidadeMarchas()),
                        String.valueOf(bicicleta.getTamanhoQuadro()), String.valueOf(bicicleta.getQuilometrosRodados()), bicicleta.getObservacao(), String.valueOf(bicicleta.getId())});
    }

    public void deleteById(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("DELETE FROM " + TABEL_BICICLETA + " WHERE id = ?",
                new String[]{String.valueOf(id) });
    }

    public List<Bicicleta> getAll(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Bicicleta> bicicletas = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT id, modelo, tamanho_aro, quantidade_marchas, tamanho_quadro, quilometros_rodados, observacao FROM " + TABEL_BICICLETA, null);
        while(cursor.moveToNext()){
            Bicicleta bicicleta = new Bicicleta(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
            bicicletas.add(bicicleta);
        }
        return bicicletas;
    }
}
