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
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.repository.BicicletaRepository;

import java.util.ArrayList;

public class CadastroBicicletaActivity extends AppCompatActivity {
    private Spinner aroSpinner, quadroSpinner;
    private EditText modeloTextView, quantidadeMarchaTextView, observacaoBicicletaEditText;
    private Button buttonNext;
    private Bicicleta bicicletaEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_bicicleta);
        iniciarComponentes();
        bicicletaEdit = (Bicicleta) getIntent().getSerializableExtra("bicicleta");
        if(bicicletaEdit != null){
            modeloTextView.setText(bicicletaEdit.getModelo());
            quantidadeMarchaTextView.setText(String.valueOf(bicicletaEdit.getQuantidadeMarchas()));
            observacaoBicicletaEditText.setText(bicicletaEdit.getObservacao());
        }
    }

    private void iniciarComponentes(){
        aroSpinner = findViewById(R.id.aroSpinner);
        quadroSpinner = findViewById(R.id.quadroSpinner);
        modeloTextView = findViewById(R.id.modeloTextView);
        quantidadeMarchaTextView = findViewById(R.id.quantidadeMarchasTextView);
        observacaoBicicletaEditText = findViewById(R.id.editTextObservacaoBicicleta);
        buttonNext = findViewById(R.id.buttonAdicionarViagem);
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
            Toast.makeText(this, "Preencha os seguintes campos: " + erros, Toast.LENGTH_LONG).show();
            return;
        }

        String modelo = String.valueOf(modeloTextView.getText());
        int marchas = Integer.parseInt(quantidadeMarchaTextView.getText().toString());
        int aro = Integer.parseInt(aroSpinner.getSelectedItem().toString());
        int quadro = Integer.parseInt(quadroSpinner.getSelectedItem().toString());
        String observacao = String.valueOf(observacaoBicicletaEditText.getText());
        BicicletaRepository repository = new BicicletaRepository(this);
        if(bicicletaEdit != null){
            Bicicleta newBicicleta = new Bicicleta(bicicletaEdit.getId(), modelo, aro, marchas, quadro, bicicletaEdit.getQuilometrosRodados(), observacao);
            repository.update(newBicicleta);
            finish();
        }else{
            Bicicleta newBicicleta = new Bicicleta(modelo, aro, marchas, quadro, observacao);
            repository.add(newBicicleta);
            handleClickNextPage();
        }
    }

    private void handleClickNextPage(){
        Intent mainIntent = new Intent(CadastroBicicletaActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}