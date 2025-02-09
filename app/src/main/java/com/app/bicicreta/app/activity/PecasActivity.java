package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.repository.PecaRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PecasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Peca> pecas = new ArrayList<>();
    private Button buttonSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecas);
        getAllPecas();
        iniciarComponentes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllPecas();
        inicializarRecycleView();
    }

    private void getAllPecas(){
        PecaRepository repository = new PecaRepository(this);
        pecas = repository.getAllWithBicicleta();
    }

    private void inicializarRecycleView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewPeca);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterPeca adapter = new AdapterPeca(pecas);
        recyclerView.setAdapter(adapter);
    }

    private void iniciarComponentes(){
        inicializarRecycleView();
        buttonSalvar = findViewById(R.id.novaBicicletaButton);
        buttonSalvar.setOnClickListener(v -> handleCadastroPeca());
    }
    private void handleCadastroPeca(){
        Intent cadastroPecaIntent = new Intent(PecasActivity.this, CadastroPecaActivity.class);
        startActivity(cadastroPecaIntent);
    }
}