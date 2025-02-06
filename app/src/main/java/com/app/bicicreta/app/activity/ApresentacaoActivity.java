package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bicicreta.R;
import com.app.bicicreta.app.repository.UserRepository;

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
        buttonNext = findViewById(R.id.buttonBicicletaNext);
        buttonNext.setOnClickListener(v -> createUser());
    }

    private void createUser(){
        UserRepository repository = new UserRepository(this);
        String name = String.valueOf(nomeEditText.getText());
        if(name.trim().isEmpty()){
            Toast.makeText(this, "Por favor, Preencha um nome.", Toast.LENGTH_LONG).show();
            return;
        }
        repository.add(name);
        handleClickNextPage();
    }

    private void handleClickNextPage(){
        Intent mainIntent = new Intent(ApresentacaoActivity.this, CadastroBicicletaActivity.class);
        startActivity(mainIntent);
        finish();
    }
}