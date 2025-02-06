package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterViagem;
import com.app.bicicreta.app.model.Viagem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViagensActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button novaViagemButton;
    private List<Viagem> viagens = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens);
        iniciarComponentes();
        mockViagem();
    }

    private void mockViagem(){
        for (int i = 0; i < 15; i++){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Viagem viagem = new Viagem(String.valueOf(simpleDateFormat.format(new Date())), "0 km", "Destino " + i);
            viagens.add(viagem);
        }
    }

    private void iniciarComponentes(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewViagens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterViagem adapter = new AdapterViagem(viagens);
        recyclerView.setAdapter(adapter);
        novaViagemButton = findViewById(R.id.novaViagemButton);
        novaViagemButton.setOnClickListener(v -> handleCadastroViagem());
    }

    private void handleCadastroViagem(){
        Intent cadastroViagemIntent = new Intent(ViagensActivity.this, CadastroViagemActivity.class);
        startActivity(cadastroViagemIntent);
    }
}