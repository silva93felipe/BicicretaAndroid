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
        db.execSQL(creteBicicletaTable());
        db.execSQL(cretePecaTable());
        db.execSQL(creteViagemTable());
        db.execSQL(creteUserTable());
        db.execSQL(creteServicoTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private String creteBicicletaTable(){
        return "CREATE TABLE bicicleta ( id INTEGER PRIMARY KEY AUTOINCREMENT, modelo VARCHAR(15), tamanho_aro INTEGER DEFAULT 0, " +
                "tamanho_quadro REAL DEFAULT 0, quantidade_marchas INTEGER DEFAULT 0, quilometros_rodados INTEGER DEFAULT 0, observacao VARCHAR(200) );";
    }

    private String cretePecaTable(){
        return "CREATE TABLE peca ( id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(15), " +
                "valor_compra REAL DEFAULT 0.0, quilometros_rodados INTEGER DEFAULT 0, data_compra DEFAULT CURRENT_TIMESTAMP, observacao VARCHAR(200), " +
                "bicicleta_id INTEGER, FOREIGN KEY (bicicleta_id) REFERENCES bicicleta (id) ); ";
    }


    private String creteServicoTable(){
        return "CREATE TABLE servico ( id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(15), " +
                "valor_servico REAL DEFAULT 0.0, quilometros_rodados INTEGER DEFAULT 0, data_servico DEFAULT CURRENT_TIMESTAMP, observacao VARCHAR(200)," +
                "bicicleta_id INTEGER, FOREIGN KEY (bicicleta_id) REFERENCES bicicleta (id) ); ";
    }


    private String creteViagemTable(){
        return "CREATE TABLE viagem ( id INTEGER PRIMARY KEY AUTOINCREMENT, destino VARCHAR(20), quilometros_rodados INTEGER DEFAULT 0, " +
                "data_viagem DEFAULT CURRENT_TIMESTAMP, bicicleta_id INTEGER, observacao VARCHAR(200), origem VARCHAR(20), FOREIGN KEY (bicicleta_id) REFERENCES bicicleta (id) ); ";
    }

    private String creteUserTable(){
        return "CREATE TABLE user ( id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT ); ";
    }
}
