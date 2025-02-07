package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.repository.BicicletaRepository;

import java.util.ArrayList;

public class CadastroBicicletaActivity extends AppCompatActivity {
    private Spinner aroSpinner, quadroSpinner;
    private EditText modeloTextView, quantidadeMarchaTextView;
    private Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_bicicleta);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        aroSpinner = findViewById(R.id.aroSpinner);
        quadroSpinner = findViewById(R.id.quadroSpinner);
        modeloTextView = findViewById(R.id.modeloTextView);
        quantidadeMarchaTextView = findViewById(R.id.quantidadeMarchasTextView);
        buttonNext = findViewById(R.id.buttonAdicionarPeca);
        buttonNext.setOnClickListener(h -> createBicicleta());
    }

    private void createBicicleta(){
        ArrayList<String> erros = new ArrayList<>();
        if(modeloTextView.getText().toString().trim().isEmpty()){
            erros.add("modelo");
        }

        if(quantidadeMarchaTextView.getText().toString().trim().isEmpty()){
            erros.add("marchas");
        }

        if(aroSpinner.getSelectedItem().toString().trim().isEmpty() || aroSpinner.getSelectedItemPosition() == 0){
            erros.add("aro");
        }

        if(quadroSpinner.getSelectedItem().toString().trim().isEmpty() || quadroSpinner.getSelectedItemPosition() == 0){
            erros.add("quadro");
        }

        if(!erros.isEmpty()){
            Toast.makeText(this, "Preencha os sequintes campos: " + erros, Toast.LENGTH_LONG).show();
            return;
        }

        String modelo = String.valueOf(modeloTextView.getText());
        int marchas = Integer.parseInt(quantidadeMarchaTextView.getText().toString());
        int aro = Integer.parseInt(aroSpinner.getSelectedItem().toString());
        int quadro = Integer.parseInt(quadroSpinner.getSelectedItem().toString());

        Bicicleta newBicicleta = new Bicicleta(modelo, aro, marchas, quadro);
        BicicletaRepository repository = new BicicletaRepository(this);
        repository.add(newBicicleta);
        handleClickNextPage();
    }

    private void handleClickNextPage(){
        Intent mainIntent = new Intent(CadastroBicicletaActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}