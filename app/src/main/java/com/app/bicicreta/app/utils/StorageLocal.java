package com.app.bicicreta.app.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageLocal {
    private Context context;
    private final String STORAGE_ARQUIVO = "storageLocal";
    private final String DATA_NOTIFICACAO_ULTIMA_VIAGEM = "dataNotificacaoUltimaViagem";
    private final String DATA_NOTIFICACAO_VIAGEM_AGENDADA = "dataNotificacaoViagemAgendada";
    public StorageLocal(Context context){
        this.context = context;
    }
    public String getDataNotificacaoUltimaViagem(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_ARQUIVO, MODE_PRIVATE);
        return sharedPreferences.getString(DATA_NOTIFICACAO_ULTIMA_VIAGEM, null);
    }

    public void setDataNotificacaoUltimaViagem(String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_ARQUIVO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( DATA_NOTIFICACAO_ULTIMA_VIAGEM, value);
        editor.apply();
    }

    public String getDataNotificacaoViagemAgendada(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_ARQUIVO, MODE_PRIVATE);
        return sharedPreferences.getString(DATA_NOTIFICACAO_VIAGEM_AGENDADA, null);
    }

    public void setDataNotificacaoViagemAgendada(String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_ARQUIVO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( DATA_NOTIFICACAO_VIAGEM_AGENDADA, value);
        editor.apply();
    }
}
