package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.app.bicicreta.R;

public class RegisterActivity extends AppCompatActivity {
    private Button buttonRestore, buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        buttonRestore = findViewById(R.id.buttonRestore);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> handleNext(ApresentacaoActivity.class));
        buttonRestore.setOnClickListener(v -> handleNext(ConfiguracoesActivity.class));
    }

    private void handleNext(Class<?> activity){
        startActivity(new Intent(RegisterActivity.this, activity));
    }
}