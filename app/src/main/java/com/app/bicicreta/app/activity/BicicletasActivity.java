package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterBicicleta;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.repository.BicicletaRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BicicletasActivity extends AppCompatActivity {
    private Button buttonFormularioBicicleta;
    private RecyclerView bicicletasRecycleView;
    private List<Bicicleta> bicicletas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicicletas);
        getAllBicicletas();
        iniciarComponentes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllBicicletas();
        inicializarRecycleView();
    }

    private void inicializarRecycleView(){
        bicicletasRecycleView = findViewById(R.id.bicicletasRecyclerView);
        bicicletasRecycleView.setLayoutManager(new LinearLayoutManager(this));
        bicicletasRecycleView.setHasFixedSize(true);
        AdapterBicicleta adapter = new AdapterBicicleta(bicicletas);
        bicicletasRecycleView.setAdapter(adapter);
    }

    private void iniciarComponentes(){
        inicializarRecycleView();
        buttonFormularioBicicleta = findViewById(R.id.novaBicicletaButton);
        buttonFormularioBicicleta.setOnClickListener(v -> handleCadastroBicicleta());
    }

    private void getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        bicicletas = repository.getAll();
    }

    private void handleCadastroBicicleta(){
        Intent intent = new Intent(BicicletasActivity.this, CadastroBicicletaActivity.class);
        startActivity(intent);
    }
}