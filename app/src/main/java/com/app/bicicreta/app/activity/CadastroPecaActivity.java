package com.app.bicicreta.app.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.PecaRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroPecaActivity extends AppCompatActivity {
    private EditText dataCompraPeca, descricaoPeca, valorPeca;
    private Spinner bicicletaSpinner;
    private Button botaoSalvarPeca;
    private Peca pecaEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_peca);
        inicializarComponentes();
        pecaEdit = (Peca) getIntent().getSerializableExtra("peca");
        if(pecaEdit != null){
            descricaoPeca.setText(pecaEdit.getNomePeca());
            valorPeca.setText(String.valueOf(pecaEdit.getValor()));
            dataCompraPeca.setText(pecaEdit.getDataCompra());
        }
    }

    private void inicializarComponentes(){
        dataCompraPeca = findViewById(R.id.dataCompraEditText);
        dataCompraPeca.setOnClickListener( v ->  {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        dataCompraPeca.setText(selectedDate);
                    },year, month, day
            );
            datePicker.show();
        });
        descricaoPeca = findViewById(R.id.descricaoPecaEditText);
        valorPeca = findViewById(R.id.valorPecaEditText);
        botaoSalvarPeca = findViewById(R.id.buttonAdicionarViagem);
        botaoSalvarPeca.setOnClickListener(v -> createPeca());
        bicicletaSpinner = findViewById(R.id.bicicletaIdPecaSpinner);
        List<ItemSpinner> itemList = new ArrayList<>();
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bicicletaSpinner.setAdapter(adapter);
    }

    public List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll();
    }

    private void createPeca(){
        List<String> erros = new ArrayList<>();
        if(descricaoPeca.getText().toString().trim().isEmpty()){
            erros.add("Descrição");
        }

        if(valorPeca.getText().toString().trim().isEmpty()){
            erros.add("Valor");
        }

        if(dataCompraPeca.getText().toString().trim().isEmpty()){
            erros.add("Data compra");
        }

        if(bicicletaSpinner.getSelectedItem().toString().trim().isEmpty()){
            erros.add("Bicicleta");
        }

        if(!erros.isEmpty()){
            Toast.makeText(this, "Preencha os sequintes campos: " + erros, Toast.LENGTH_LONG).show();
            return;
        }

        ItemSpinner bicicletaSelecionada = (ItemSpinner) bicicletaSpinner.getSelectedItem();
        PecaRepository repository = new PecaRepository(this);
        if(pecaEdit != null){
            Peca newPeca = new Peca(pecaEdit.getId(), descricaoPeca.getText().toString(), dataCompraPeca.getText().toString(),
                    Double.parseDouble(valorPeca.getText().toString()), pecaEdit.getQuilometros(), bicicletaSelecionada.getId());
            repository.update(newPeca);
        }else{
            Peca newPeca = new Peca(descricaoPeca.getText().toString(), dataCompraPeca.getText().toString(), Double.parseDouble(valorPeca.getText().toString()), bicicletaSelecionada.getId());
            repository.add(newPeca);
        }
        finish();
    }
}