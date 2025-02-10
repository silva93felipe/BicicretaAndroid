package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Viagem;

import java.util.ArrayList;
import java.util.List;

public class PecaRepository {
    private final String TABELA_PECA = "peca";
    private final String TABEL_BICICLETA = "bicicleta";
    BicicretaDbHelper db;
    public PecaRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(Peca peca){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descricao", peca.getNomePeca());
        values.put("valor_compra", peca.getValor());
        values.put("quilometros_rodados", peca.getQuilometros());
        values.put("data_compra", peca.getDataCompra());
        values.put("bicicleta_id", peca.getBicicletaId());
        con.insert(TABELA_PECA, null, values);
    }

    public void updateQuilometrosRodadosByBicicletaId(int bicicletaId, int quilometros){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABELA_PECA + " SET quilometros_rodados = quilometros_rodados + ? WHERE id = ?", new String[]{ String.valueOf(quilometros),  String.valueOf(bicicletaId)});
    }

    public List<Peca> getLastByParam(int quantidade){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Peca> pecas = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT p.id, p.descricao, p.data_compra, p.valor_compra, p.quilometros_rodados, p.bicicleta_id, b.modelo  FROM "
                + TABELA_PECA + " p"
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = p.bicicleta_id "
                + " ORDER BY p.data_compra DESC LIMIT ? ", new String[]{ String.valueOf(quantidade)});
        while(cursor.moveToNext()){
            Peca peca = new Peca(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
            pecas.add(peca);
        }
        return pecas;
    }

    public Peca getByIdWithBicicleta(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        Cursor cursor = con.rawQuery("SELECT p.id, p.descricao, p.data_compra, p.valor_compra, p.quilometros_rodados, p.bicicleta_id, b.modelo  FROM "
                + TABELA_PECA + " p"
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = p.bicicleta_id "
                + " WHERE id = ?", new String[]{ String.valueOf(id)});
        while(cursor.moveToNext()){
            return new Peca(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
        }
        return null;
    }

    public List<Peca> getAllWithBicicleta(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Peca> pecas = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT p.id, p.descricao, p.data_compra, p.valor_compra, p.quilometros_rodados, p.bicicleta_id, b.modelo  FROM "
                + TABELA_PECA + " p"
                + " INNER JOIN " + TABEL_BICICLETA + " b ON b.id = p.bicicleta_id ", null);
        while(cursor.moveToNext()){
            Peca peca = new Peca(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
            pecas.add(peca);
        }
        return pecas;
    }
}
