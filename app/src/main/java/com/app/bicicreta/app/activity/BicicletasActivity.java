package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterPeca;

public class BicicletasActivity extends AppCompatActivity {
    private Button buttonFormularioBicicleta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicicletas);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        buttonFormularioBicicleta = findViewById(R.id.novaBicicletaButton);
        buttonFormularioBicicleta.setOnClickListener(v -> handleCadastroBicicleta());
    }
    private void handleCadastroBicicleta(){
        Intent intent = new Intent(BicicletasActivity.this, CadastroBicicletaActivity.class);
        startActivity(intent);
    }
}