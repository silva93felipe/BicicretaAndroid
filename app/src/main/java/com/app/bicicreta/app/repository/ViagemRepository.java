package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.GraficoViagem;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.utils.DataUtil;

import java.time.LocalDate;
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
        values.put("observacao", viagem.getObservacao());
        values.put("origem", viagem.getOrigem());
        con.insert(TABLE_VIAGEM, null, values);
    }

    public List<Viagem> getLastByParam(int quantidade){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT id, data_viagem, quilometros_rodados, destino, bicicleta_id, observacao, origem  FROM " + TABLE_VIAGEM + " WHERE data_viagem <= ? ORDER BY data_viagem DESC LIMIT ?;", new String[]{ String.valueOf(DataUtil.dataAtualString()), String.valueOf(quantidade) });
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAllByBicicletaId(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT v.id, v.data_viagem, v.quilometros_rodados, v.destino, v.bicicleta_id, b.modelo, v.observacao, v.origem  FROM "
                + TABLE_VIAGEM + " v "
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = v.bicicleta_id "
                + " WHERE v.bicicleta_id = ? "
                + " ORDER BY data_viagem DESC;",  new String[]{ String.valueOf(id)});
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),  cursor.getString(7));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAllPendentes(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT v.id, v.data_viagem, v.quilometros_rodados, v.destino, v.bicicleta_id, b.modelo, v.observacao, v.origem  FROM "
                + TABLE_VIAGEM + " v "
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = v.bicicleta_id "
                + " WHERE v.data_viagem > ?"
                + " ORDER BY data_viagem DESC;",  new String[]{ String.valueOf(DataUtil.dataAtualString())});
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),  cursor.getString(7));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAllPendentesByBicicletaId(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT v.id, v.data_viagem, v.quilometros_rodados, v.destino, v.bicicleta_id, b.modelo, v.observacao, v.origem  FROM "
                + TABLE_VIAGEM + " v "
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = v.bicicleta_id "
                + " WHERE v.bicicleta_id = ? AND v.data_viagem > ?"
                + " ORDER BY data_viagem DESC;",  new String[]{ String.valueOf(id), String.valueOf(DataUtil.dataAtualString())});
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),  cursor.getString(7));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAll(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT v.id, v.data_viagem, v.quilometros_rodados, v.destino, v.bicicleta_id, b.modelo, v.observacao, v.origem  FROM "
                                          + TABLE_VIAGEM + " v "
                                          + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = v.bicicleta_id "
                                          + " ORDER BY data_viagem DESC;", null);
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),  cursor.getString(7));
            viagens.add(viagem);
        }
        return viagens;
    }

    public List<Viagem> getAllByPeridoAnBicicletaId(String dataInicial, String dataFinal, int bicicletaId){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Viagem> viagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT v.id, v.data_viagem, v.quilometros_rodados, v.destino, v.bicicleta_id, b.modelo, v.observacao, v.origem  FROM "
                + TABLE_VIAGEM + " v "
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = v.bicicleta_id "
                + " WHERE v.data_viagem >= ? AND v.data_viagem <= ? "
                + " AND  v.bicicleta_id = ?"
                + " ORDER BY data_viagem DESC;", new String[]{ String.valueOf(dataInicial), String.valueOf(dataFinal),  String.valueOf(bicicletaId)});
        while(cursor.moveToNext()){
            Viagem viagem = new Viagem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),  cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6),  cursor.getString(7));
            viagens.add(viagem);
        }
        return viagens;
    }

    public int getTotalViagens(){
        SQLiteDatabase con = db.getWritableDatabase();
        int quantidade =0;
        Cursor cursor = con.rawQuery(" SELECT COUNT(*) FROM " + TABLE_VIAGEM  + " WHERE data_viagem <= ? ;", new String[]{ String.valueOf(DataUtil.dataAtualString())});
        while(cursor.moveToNext()){
            quantidade = cursor.getInt(0);
        }
        return quantidade;
    }

    public void update(Viagem viagem){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABLE_VIAGEM + " SET destino = ?, quilometros_rodados = ?, data_viagem = ?, bicicleta_id = ?, observacao = ?, origem = ? WHERE id = ?;",
                new String[]{ String.valueOf(viagem.getDestino()),  String.valueOf(viagem.getQuilometros()),
                              viagem.getData(), String.valueOf(viagem.getBicicletaId()), viagem.getObservacao(), viagem.getOrigem(), String.valueOf(viagem.getId())});
    }

    public int totalQuilometrosRodados(){
        SQLiteDatabase con = db.getWritableDatabase();
        int total = 0;
        Cursor cursor = con.rawQuery("SELECT SUM(quilometros_rodados) FROM " + TABLE_VIAGEM + " WHERE data_viagem <= ? ;", new String[]{ String.valueOf(DataUtil.dataAtualString())});
        while(cursor.moveToNext()){
            total = cursor.getInt(0);
        }
        return total;
    }

    public List<GraficoViagem> totalViagemPorMes(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<GraficoViagem> graficoViagens = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT STRFTIME('%m', data_viagem), COUNT(*) FROM " + TABLE_VIAGEM + " WHERE data_viagem <= ? GROUP BY 1 LIMIT 5;", new String[]{ String.valueOf(DataUtil.dataAtualString())});
        while(cursor.moveToNext()){
            if( cursor.getString(0) != null) {
                graficoViagens.add(new GraficoViagem(cursor.getInt(1), cursor.getString(0)));
            }
        }
        return graficoViagens;
    }

    public void deleteById(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("DELETE FROM " + TABLE_VIAGEM + " WHERE id = ?;",  new String[]{ String.valueOf(id)});
    }



}
