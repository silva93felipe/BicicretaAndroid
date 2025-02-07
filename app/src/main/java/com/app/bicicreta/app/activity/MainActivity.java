package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.User;
import com.app.bicicreta.app.repository.UserRepository;

public class MainActivity extends AppCompatActivity {
    TextView nomeUsarioTextView;
    TextView quilomentrosRodadosTextView;
    RecyclerView ultimasViagensRecyclerView;
    ImageView mapTabImagemView;
    ImageView toolTabImagemView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        getNomeUsuario();
    }
    private void inicializarComponentes(){
        nomeUsarioTextView = findViewById(R.id.nomeUsuarioTextView);
        mapTabImagemView = findViewById(R.id.mapTabImagemView);
        mapTabImagemView.setOnClickListener(v -> handleNavigation(ViagensActivity.class));
        toolTabImagemView = findViewById(R.id.toolTabImagemView);
        toolTabImagemView.setOnClickListener(v -> handleNavigation(PecasActivity.class));
        quilomentrosRodadosTextView = findViewById(R.id.quilometrosRodadosTextView);
       // ultimasViagensRecyclerView = findViewById(R.id.ultimasViagensRecyclerView);
       // ultimasViagensRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ultimasViagensRecyclerView.setHasFixedSize(true);
    }

    private void handleNavigation(Class activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    private void getNomeUsuario(){
        UserRepository repository = new UserRepository(this);
        User user = repository.getOne();
        if(user == null){
            nomeUsarioTextView.setText("Olá, DESCONHECIDO!" );
        }else{
            nomeUsarioTextView.setText("Olá, " + user.getNome() );
        }

    }

}