package com.app.bicicreta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.bicicreta.R;
import com.app.bicicreta.activity.adapter.AdapterViagem;
import com.app.bicicreta.activity.model.Peca;
import com.app.bicicreta.activity.model.Viagem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ViagensActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
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
            Viagem viagem = new Viagem();
            viagem.setDestino("Destino " + i);
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            viagem.setData(String.valueOf(simpleDateFormat.format(new Date())));
            viagem.setNomeBicicleta("Bicicleta 1");
            viagem.setQuilometros("0");
            viagens.add(viagem);
        }
    }

    public void iniciarComponentes(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewViagens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterViagem adapter = new AdapterViagem(viagens);
        recyclerView.setAdapter(adapter);
    }
}