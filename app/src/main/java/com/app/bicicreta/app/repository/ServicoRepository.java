package com.app.bicicreta.app.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Servico;

import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {
    private final String TABELA_SERVICO = "servico";
    private final String TABELA_BICICLETA = "bicicleta";
    private BicicretaDbHelper db;
    public ServicoRepository(Context context){
        db = new BicicretaDbHelper(context);
    }

    public void add(Servico servico){
        SQLiteDatabase con = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descricao", servico.getDescricao());
        values.put("valor_servico", servico.getValor());
        values.put("quilometros_rodados", servico.getQuilometros());
        values.put("data_servico", servico.getDataServico());
        values.put("bicicleta_id", servico.getBicicletaId());
        values.put("observacao", servico.getObservacao());
        con.insert(TABELA_SERVICO, null, values);
    }

    public void deleteById(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("DELETE FROM " + TABELA_SERVICO + " WHERE id = ?",
                new String[]{ String.valueOf(id)});
    }

    public void update(Servico servico){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABELA_SERVICO + " SET descricao = ?, data_servico = ?, valor_servico = ?, quilometros_rodados = ?, " +
                        " bicicleta_id = ?, observacao = ? WHERE id = ?",
                new String[]{ String.valueOf(servico.getDescricao()),  String.valueOf(servico.getDataServico()), String.valueOf(servico.getValor()),
                        String.valueOf(servico.getQuilometros()), String.valueOf(servico.getBicicletaId()), String.valueOf(servico.getId()), servico.getObservacao()});
    }

    public int getTotalServicos(){
        SQLiteDatabase con = db.getWritableDatabase();
        int quantidade =0;
        Cursor cursor = con.rawQuery(" SELECT COUNT(*) FROM " + TABELA_SERVICO  + ";" , null);
        while(cursor.moveToNext()){
            quantidade = cursor.getInt(0);
        }
        return quantidade;
    }

    public void updateQuilometrosRodadosByBicicletaId(int bicicletaId, int quilometros){
        SQLiteDatabase con = db.getWritableDatabase();
        con.execSQL("UPDATE " + TABELA_SERVICO + " SET quilometros_rodados = quilometros_rodados + ? WHERE id = ?", new String[]{ String.valueOf(quilometros),  String.valueOf(bicicletaId)});
    }

    public List<Servico> getLastByParam(int quantidade){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Servico> servicos = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT p.id, p.data_servico, p.valor_servico, p.quilometros_rodados, p.bicicleta_id, p.descricao, b.modelo, p.observacao  FROM "
                + TABELA_SERVICO + " p"
                + " INNER JOIN " + TABELA_BICICLETA + " b ON b.id = p.bicicleta_id "
                + " ORDER BY p.data_compra DESC LIMIT ? ", new String[]{ String.valueOf(quantidade)});
        while(cursor.moveToNext()){
            Servico servico = new Servico(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2),  cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            servicos.add(servico);
        }
        return servicos;
    }

    public Servico getByIdWithBicicleta(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        Cursor cursor = con.rawQuery("SELECT p.id, p.data_servico, p.valor_servico,  p.quilometros_rodados, p.bicicleta_id, p.descricao, b.modelo, p.observacao  FROM "
                + TABELA_SERVICO + " p"
                + " INNER JOIN " + TABELA_BICICLETA + " b ON b.id = p.bicicleta_id "
                + " WHERE id = ?", new String[]{ String.valueOf(id)});
        while(cursor.moveToNext()){
            return new Servico(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        }
        return null;
    }

    public List<Servico> getAllWithBicicleta(){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Servico> servicos = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT p.id, p.data_servico, p.valor_servico,  p.quilometros_rodados, p.bicicleta_id, p.descricao, b.modelo, p.observacao  FROM "
                + TABELA_SERVICO + " p"
                + " INNER JOIN " + TABELA_BICICLETA + " b ON b.id = p.bicicleta_id ", null);
        while(cursor.moveToNext()){
            Servico servico = new Servico(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            servicos.add(servico);
        }
        return servicos;
    }

    public List<Servico> getAllByBicicletaId(int id){
        SQLiteDatabase con = db.getWritableDatabase();
        List<Servico> servicos = new ArrayList<>();
        Cursor cursor = con.rawQuery("SELECT p.id, p.data_servico, p.valor_servico,  p.quilometros_rodados, p.bicicleta_id, p.descricao, b.modelo, p.observacao  FROM "
                + TABELA_SERVICO + " p"
                + " INNER JOIN " + TABELA_BICICLETA + " b ON b.id = p.bicicleta_id "
                + " WHERE p.bicicleta_id = ?", new String[]{ String.valueOf(id)});
        while(cursor.moveToNext()){
            Servico servico = new Servico(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            servicos.add(servico);
        }
        return servicos;
    }
}
