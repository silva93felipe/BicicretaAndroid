package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
    }

    private void inicializarComponentes(){
        buttonNext = findViewById(R.id.buttonBicicletaNext);
        buttonNext.setOnClickListener(v -> handleNext());
    }

    private void handleNext(){
        startActivity(new Intent(BemVindoActivity.this, ApresentacaoActivity.class));
//        if(jaTemUsuarioCadastrado()){
//            startActivity(new Intent(BemVindoActivity.this, MainActivity.class));
//        }else{
//            startActivity(new Intent(BemVindoActivity.this, ApresentacaoActivity.class));
//        }
        finish();
    }

    private boolean jaTemUsuarioCadastrado(){
        UserRepository repository = new UserRepository(this);
        Cursor cursor = repository.getOne();
        return cursor.moveToNext();
    }
}