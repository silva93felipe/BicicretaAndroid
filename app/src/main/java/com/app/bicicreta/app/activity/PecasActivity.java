package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.repository.PecaRepository;

import java.util.ArrayList;
import java.util.List;

public class PecasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Peca> pecas = new ArrayList<>();
    private Button buttonSalvar;
    private TextView nadaExibirPecaTextView;
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
        exibirMessageListaVazia();
    }

    private void getAllPecas(){
        PecaRepository repository = new PecaRepository(this);
        pecas = repository.getAllWithBicicleta();
    }

    private void inicializarRecycleView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewPeca);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterPeca adapter = new AdapterPeca(pecas, p -> {
            handleAtualizarPeca(p);
        });
        recyclerView.setAdapter(adapter);
    }

    private void exibirMessageListaVazia(){
        if(pecas.isEmpty()){
            nadaExibirPecaTextView.setVisibility(View.VISIBLE);
        }else{
            nadaExibirPecaTextView.setVisibility(View.GONE);
        }
    }

    private void iniciarComponentes(){
        inicializarRecycleView();
        buttonSalvar = findViewById(R.id.buttonNovaPeca);
        buttonSalvar.setOnClickListener(v -> handleCadastroPeca());
        nadaExibirPecaTextView = findViewById(R.id.nadaExibirPecaTextView);
        exibirMessageListaVazia();
    }
    private void handleCadastroPeca(){
        Intent cadastroPecaIntent = new Intent(PecasActivity.this, CadastroPecaActivity.class);
        startActivity(cadastroPecaIntent);
    }
    private void handleAtualizarPeca(Peca peca){
        Intent intent = new Intent(PecasActivity.this, CadastroPecaActivity.class);
        intent.putExtra("peca", peca);
        startActivity(intent);
    }
}