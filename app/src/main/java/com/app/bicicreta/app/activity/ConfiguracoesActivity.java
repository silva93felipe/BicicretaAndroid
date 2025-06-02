package com.app.bicicreta.app.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.bicicreta.R;
import com.app.bicicreta.app.db.BicicretaDbHelper;
import com.app.bicicreta.app.utils.DataUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ConfiguracoesActivity extends AppCompatActivity {
    private Button btnRestore, btnBackup;
    private ProgressBar progressBarRestore;
    private List<String> tabelas = new ArrayList<>();
    private String VERSION = "1.0";
    private String INF = "Bicicreta";
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        btnBackup = findViewById(R.id.buttonBackup);
        btnBackup.setOnClickListener(v -> {
            if(temPermissao()){
                backupCSV();
            }
        });
        btnRestore = findViewById(R.id.btnRestore);
        btnRestore.setOnClickListener(v -> {
            if(temPermissao()){
                restore();
            }
        });
        progressBarRestore = findViewById(R.id.progressBarRestore);
        progressBarRestore.setVisibility(View.GONE);
        // Tabelas
        tabelas.add("bicicleta");
        tabelas.add("peca");
        tabelas.add("viagem");
        tabelas.add("user");
        tabelas.add("servico");
    }

    private boolean temPermissao(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
                return false;
            }
        }
        return true;
    }

    public void mudarEstadoDoButtons(boolean isHidden){
        if(isHidden){
            btnBackup.setVisibility(View.GONE);
            btnRestore.setVisibility(View.GONE);
            progressBarRestore.setVisibility(View.VISIBLE);
            return;
        }

        btnBackup.setVisibility(View.VISIBLE);
        btnRestore.setVisibility(View.VISIBLE);
        progressBarRestore.setVisibility(View.GONE);
    }

    private void restore(){
        mudarEstadoDoButtons(true);
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione o backup"), 1);
    }
    private void backupCSV(){
        mudarEstadoDoButtons(true);
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Bicicreta");
        if(!exportDir.exists()){
            exportDir.mkdirs();
        }
        File file = new File(exportDir, "backup.csv");
        try{
            String DATA_BACKUP = DataUtil.dataAtualString();
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("#VER," + VERSION + "\n");
            bufferedWriter.write("#INF," + INF + "\n");
            bufferedWriter.write("#DAT," + DATA_BACKUP + "\n");
            SQLiteDatabase db = new BicicretaDbHelper(this).getWritableDatabase();
            for(int i = 0; i < tabelas.size(); i++){
                Cursor cur = db.rawQuery("SELECT * FROM " + tabelas.get(i), null);
                bufferedWriter.write("#TAB," + tabelas.get(i) + ",");
                if(cur.getColumnCount() > 0){
                    for(int j = 0; j < cur.getColumnCount(); j++){
                        bufferedWriter.write(cur.getColumnName(j) + (j < (cur.getColumnCount() - 1) ? "," : "\n"));
                    }
                }
                while(cur.moveToNext()){
                    bufferedWriter.write("#ROW,");
                    for(int j = 0; j < cur.getColumnCount(); j++){
                        String campo = cur.getString(j)
                                        .replace("\n", "&n;")
                                        .replace("\r", "&r;")
                                        .replace("\t", "&t;")
                                        .replace(",", "&v;");
                        bufferedWriter.write(campo + (j < (cur.getColumnCount() - 1) ? "," : "\n"));
                    }
                }
                cur.close();
            }
            db.close();
            bufferedWriter.flush();
            Toast.makeText(this, "Backup realizado com sucesso", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("BICICRETA", "BACKUP CSV: " + e.getMessage());
        }finally {
            mudarEstadoDoButtons(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                restoreCSV(uri);
            }
        }else{
            mudarEstadoDoButtons(false);
            Toast.makeText(this, "Operação cancelada", Toast.LENGTH_LONG).show();
        }
    }

    private void restoreCSV(Uri uri) {
        try{
            mudarEstadoDoButtons(true);
            String bckVer = "";
            String bckInf = "";
            String bckDat = "";
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int l = 3;
            while ((line = reader.readLine()) != null && l > 0) {
                String[] row = line.split(",");
                if(row.length >= 2){
                    if(row[0].equals("#VER")){
                        bckVer = row[1];
                    }else if(row[0].equals("#INF")){
                        bckInf = row[1];
                    }else if(row[0].equals("#DAT")){
                        bckDat = row[1];
                    }
                }
                l--;
            }
            reader.close();
            if(bckVer.equals("1.0")){
                restoreFullCSV(uri);
            }else{
                Toast.makeText(this, "Erro ao restaurar! Formato incompatível!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao restaurar backup!", Toast.LENGTH_SHORT).show();
        }finally {
            mudarEstadoDoButtons(false);
        }
    }

    private void restoreFullCSV(Uri uri){
        try{
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            SQLiteDatabase db = new BicicretaDbHelper(this).getWritableDatabase();
            for(int i = 0; i < tabelas.size(); i++){
                db.rawQuery("DELETE FROM " + tabelas.get(i), null);
            }

            String tab = "";
            String[] row;
            List<String> cols = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null){
                row = line.split(",");
                if(row.length >= 2){
                    if(row[0].equals("#TAB")){ // Pega tabela
                        tab = row[1];
                        cols.clear();
                        for(int c = 2; c < row.length; c++){
                            cols.add(row[c]);
                        }
                    }else if(row[0].equals("#ROW")){ // Pega a linha
                        ContentValues values = new ContentValues();
                        for(int c = 1; c < row.length; c++){
                            String campo = row[c]
                                    .replace("&n;","\n")
                                    .replace("&r;", "\r")
                                    .replace("&t;", "\t")
                                    .replace("&v;", ",");
                            values.put(cols.get(c - 1), campo);
                        }
                        db.insert(tab, null, values);
                    }
                }
            }
            reader.close();
            db.close();
            mudarEstadoDoButtons(false);
            Toast.makeText(this, "Backup restaurado com sucesso!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
    }
}