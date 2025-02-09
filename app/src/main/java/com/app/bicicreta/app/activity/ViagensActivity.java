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
import com.app.bicicreta.app.repository.ViagemRepository;

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
        getAllViagens();
        iniciarComponentes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllViagens();
        inicializarRecycleView();
    }

    private void getAllViagens(){
        ViagemRepository repository = new ViagemRepository(this);
        viagens = repository.getAll();
    }

    private void iniciarComponentes(){
        inicializarRecycleView();
        novaViagemButton = findViewById(R.id.novaBicicletaButton);
        novaViagemButton.setOnClickListener(v -> handleCadastroViagem());
    }

    private void inicializarRecycleView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewViagens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterViagem adapter = new AdapterViagem(viagens);
        recyclerView.setAdapter(adapter);
    }


    private void handleCadastroViagem(){
        Intent cadastroViagemIntent = new Intent(ViagensActivity.this, CadastroViagemActivity.class);
        startActivity(cadastroViagemIntent);
    }
}