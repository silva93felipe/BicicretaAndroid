package com.app.bicicreta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.app.bicicreta.R;

public class BemVindoActivity extends AppCompatActivity {
    TextView buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(v -> handleNext());
    }

    private void handleNext(){
        startActivity(new Intent(BemVindoActivity.this, ApresentacaoActivity.class));
        finish();
    }
}