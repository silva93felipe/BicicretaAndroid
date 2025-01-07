package com.app.bicicreta.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bicicreta.R;

public class ApresentacaoActivity extends AppCompatActivity {
    private EditText nomeEditText;
    private Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        nomeEditText = findViewById(R.id.nomeEditText);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(v -> handleClickNextPage());
    }

    private void handleClickNextPage(){
        Intent mainIntent = new Intent(ApresentacaoActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}