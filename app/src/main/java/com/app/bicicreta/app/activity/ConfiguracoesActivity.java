package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.bicicreta.R;

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
    private Button btnRestore;
    private Button  btnBackup;
    private ProgressBar progressBarRestore;
    private Handler handler = new Handler();
    private String nomeBackup, processoMessangem;
    private int processoCompleto = 1;
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

    private void compartilharBackup() {
        File arquivo = new File(getApplicationContext().getCacheDir(), nomeBackup);
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("application/x-zip");
        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "br.hssoftware.kbike.fileprovider", arquivo); //TEM QUE FAZER ALGO AQUI
        intentShare.putExtra(Intent.EXTRA_STREAM, uri);
        intentShare.putExtra(Intent.EXTRA_TEXT, nomeBackup);
        intentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intentShare, "Backup de Segurança"));
        //new BicicretaDbHelper(getApplicationContext()). Backup().updateDataBackup();
    }

    private void backup(){
        mudarEstadoDoButtons(true);
        Toast.makeText(this, "Iniciando o backup, seja paciente!", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nomeBackup = gerarNomeBackupZip();
                    int bufferSize = 64 * 1024;
                    InputStream is = new FileInputStream(new File(getApplicationContext().getDatabasePath(NOME_BANCO).getAbsolutePath()));
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(getApplicationContext().getCacheDir(), nomeBackup)));

                    ZipEntry entry = new ZipEntry(NOME_BANCO);
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[bufferSize];
                    int count;
                    while((count = is.read(buffer, 0, bufferSize)) > 0){
                        zos.write(buffer, 0, count);
                    }
                    is.close();
                    zos.close();
                } catch (IOException e) {
                    processoCompleto = 0;
                    processoMessangem = e.getMessage();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mudarEstadoDoButtons(false);
                        if (processoCompleto != 1){
                            Toast.makeText(getApplicationContext(), "Não foi possível fazer o backup! Detalhes: " + processoMessangem, Toast.LENGTH_LONG).show();
                            return;
                        }
                        compartilharBackup();
                    }
                });
            }
        }).start();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    int bufferSize = 64 * 1024;
                    OutputStream os = new FileOutputStream(new File(getDatabasePath(NOME_BANCO).getAbsolutePath()));
                    InputStream is = new FileInputStream(new File(getCacheDir(), NOME_BANCO));
                    byte[] buffer = new byte[bufferSize];
                    int count;
                    while((count = is.read(buffer, 0, bufferSize)) > 0){
                        os.write(buffer, 0, count);
                    }
                    os.close();
                    is.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}