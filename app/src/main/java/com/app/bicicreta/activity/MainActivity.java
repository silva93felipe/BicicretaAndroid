package com.app.bicicreta.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;

public class MainActivity extends AppCompatActivity {
    TextView nomeUsarioTextView;
    TextView quilomentrosRodadosTextView;
    RecyclerView ultimasViagensRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
    }
    private void inicializarComponentes(){
        nomeUsarioTextView = findViewById(R.id.nomeUsuarioTextView);
        quilomentrosRodadosTextView = findViewById(R.id.quilometrosRodadosTextView);
        ultimasViagensRecyclerView = findViewById(R.id.ultimasViagensRecyclerView);
        ultimasViagensRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimasViagensRecyclerView.setHasFixedSize(true);
    }
}