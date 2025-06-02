package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.repository.UserRepository;

public class BemVindoActivity extends AppCompatActivity {
    TextView buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);
        inicializarComponentes();
        jaTemUsuarioCadastrado();
    }

    private void inicializarComponentes(){
        buttonNext = findViewById(R.id.buttonPecaFragment);
        buttonNext.setOnClickListener(v -> handleNext());
    }

    private void handleNext(){
        startActivity(new Intent(BemVindoActivity.this, RegisterActivity.class));
    }

    private void jaTemUsuarioCadastrado(){
        UserRepository repository = new UserRepository(this);
        if(repository.getOne() != null){
            startActivity(new Intent(BemVindoActivity.this, MainActivity.class));
            finish();
        }
    }
}