package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.GraficoViagem;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.utils.DataUtil;

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
        Cursor cursor = con.rawQuery("SELECT id, data_viagem, quilometros_rodados, destino, bicicleta_id  FROM " + TABLE_VIAGEM + " ORDER BY data_viagem DESC LIMIT ?;", new String[]{ String.valueOf(quantidade) });
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAll(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT v.id, v.data_viagem, v.quilometros_rodados, v.destino, v.bicicleta_id, b.modelo  FROM "
                                          + TABLE_VIAGEM + " v "
                                          + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = v.bicicleta_id "
                                          + " ORDER BY data_viagem DESC;", null);
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5));
            viagens.add(viagem);
        }
        return viagens;
    }

    public int getTotalViagens(){
        SQLiteDatabase con = db.getWritableDatabase();
        int quantidade =0;
        Cursor cursor = con.rawQuery(" SELECT COUNT(*) FROM " + TABLE_VIAGEM  + ";" , null);
        while(cursor.moveToNext()){
            quantidade = cursor.getInt(0);
        }
        return quantidade;
    }

    public void update(Viagem viagem){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABLE_VIAGEM + " SET destino = ?, quilometros_rodados = ?, data_viagem = ?, bicicleta_id = ? WHERE id = ?;",
                new String[]{ String.valueOf(viagem.getDestino()),  String.valueOf(viagem.getQuilometros()),
                              String.valueOf(viagem.getData()), String.valueOf(viagem.getBicicletaId()), String.valueOf(viagem.getId())});
    }

    public int totalQuilometrosRodados(){
        SQLiteDatabase con = db.getWritableDatabase();
        int total = 0;
        Cursor cursor = con.rawQuery("SELECT SUM(quilometros_rodados) FROM " + TABLE_VIAGEM + ";", null);
        while(cursor.moveToNext()){
            total = cursor.getInt(0);
        }
        return total;
    }

    public List<GraficoViagem> totalViagemPorMes(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<GraficoViagem> graficoViagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT STRFTIME('%m', data_viagem), COUNT(*) FROM " + TABLE_VIAGEM + " GROUP BY 1 LIMIT 5;", null);
        while(cursor.moveToNext()){
            if( cursor.getString(0) != null) {
                graficoViagens.add(new GraficoViagem(cursor.getInt(1), cursor.getString(0)));
            }
        }
        return graficoViagens;
    }
}
