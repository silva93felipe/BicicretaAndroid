package com.app.bicicreta.app.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.bicicreta.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ConfiguracoesActivity extends AppCompatActivity {
    private Button btnRestore, btnBackup;
    private ProgressBar progressBarRestore;
    private String nomeBackup, processoMessangem;
    private final String NOME_BANCO = "bicreta.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        btnBackup = findViewById(R.id.buttonBackup);
        btnBackup.setOnClickListener(v -> backup());
        btnRestore = findViewById(R.id.btnRestore);
        btnRestore.setOnClickListener(v -> restore());
        progressBarRestore = findViewById(R.id.progressBarRestore);
        progressBarRestore.setVisibility(View.GONE);
    }

    private String gerarNomeBackupZip(){
        String data = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        try {
            data = format.format(new Date());
        }catch (Exception e){}
        return "bicicreta_" + data + ".zip";
    }

    private void backup() {
        mudarEstadoDoButtons(true);
        Toast.makeText(this, "Processo iniciado...", Toast.LENGTH_SHORT).show();
        File dbFile = getApplicationContext().getDatabasePath(NOME_BANCO);
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(dbFile))) {
            ContentResolver resolver = getApplicationContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, NOME_BANCO);
            contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/*");
            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            if (uri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(uri)) {
                    if (outputStream != null) {
                        int data = fis.read();
                        while (data != -1) {
                            outputStream.write(data);
                            data = fis.read();
                        }
                        outputStream.flush();
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mudarEstadoDoButtons(false);
            Toast.makeText(this, "Processo finalizado...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    int bufferSize = 64 * 1024;
//                    OutputStream os = new FileOutputStream(new File(getDatabasePath(NOME_BANCO).getAbsolutePath()));
//                    Uri fileUri = getFileUriFromDownloads(getApplicationContext(), "bicreta.db");
//                    InputStream is = getApplicationContext().getContentResolver().openInputStream(fileUri);
//                    byte[] buffer = new byte[bufferSize];
//                    int count;
//                    while((count = is.read(buffer, 0, bufferSize)) > 0){
//                        os.write(buffer, 0, count);
//                    }
//                    os.close();
//                    is.close();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            finish();
//                        }
//                    });
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1 && requestCode == 1){
            String uriBackup = data.getData().getPath();
            int bufferSize = 64 * 1024;
            try {
                OutputStream os = new FileOutputStream(new File(getDatabasePath(NOME_BANCO).getAbsolutePath()));
                //Uri fileUri = getFileUriFromDownloads(getApplicationContext(), "bicreta.db");
                InputStream is = new FileInputStream("aaa");
                byte[] buffer = new byte[bufferSize];
                int count;
                while((count = is.read(buffer, 0, bufferSize)) > 0){
                    os.write(buffer, 0, count);
                }
                os.close();
                is.close();
            }catch (Exception e ){

            }
        }
    }
}