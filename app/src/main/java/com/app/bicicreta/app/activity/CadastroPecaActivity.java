package com.app.bicicreta.app.activity;

import android.annotation.SuppressLint;
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
import com.app.bicicreta.app.utils.MoedaUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroPecaActivity extends AppCompatActivity {
    private EditText dataCompraPeca, descricaoPeca, valorPeca, observacaoPeca;
    private Spinner bicicletaSpinner;
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
            observacaoPeca.setText(pecaEdit.getObservacao());
        }
    }

    private void iniciarCalendario(){
        dataCompraPeca = findViewById(R.id.dataCompraEditText);
        dataCompraPeca.setOnClickListener( v ->  {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        @SuppressLint("DefaultLocale") String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        dataCompraPeca.setText(selectedDate);
                    },year, month, day
            );
            datePicker.show();
        });
    }

    private void iniciarSpinnerBicicleta(){
        bicicletaSpinner = findViewById(R.id.bicicletaIdPecaSpinner);
        List<ItemSpinner> itemList = new ArrayList<>();
        if(temMaisDeUmaBicicleta())
            itemList.add(new ItemSpinner(0, "Selecione uma bicicleta"));
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bicicletaSpinner.setAdapter(adapter);
    }


    private void inicializarComponentes(){
        iniciarCalendario();
        iniciarSpinnerBicicleta();
        descricaoPeca = findViewById(R.id.descricaoPecaEditText);
        observacaoPeca = findViewById(R.id.editTextObservacaoPeca);
        valorPeca = findViewById(R.id.valorPecaEditText);
        Button botaoSalvarPeca = findViewById(R.id.buttonAdicionarViagem);
        botaoSalvarPeca.setOnClickListener(v -> createPeca());
    }

    public List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll();
    }

    public boolean temMaisDeUmaBicicleta(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll().size() > 1;
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
            Toast.makeText(this, "Preencha os seguintes campos: " + erros, Toast.LENGTH_LONG).show();
            return;
        }

        ItemSpinner bicicletaSelecionada = (ItemSpinner) bicicletaSpinner.getSelectedItem();

        PecaRepository repository = new PecaRepository(this);
        if(pecaEdit != null){
            Peca newPeca = new Peca(pecaEdit.getId(), descricaoPeca.getText().toString(), dataCompraPeca.getText().toString(),
                    Double.parseDouble(valorPeca.getText().toString()), pecaEdit.getQuilometros(), bicicletaSelecionada.getId(), observacaoPeca.getText().toString());
            repository.update(newPeca);
        }else{
            Peca newPeca = new Peca(descricaoPeca.getText().toString(), dataCompraPeca.getText().toString(), Double.parseDouble(valorPeca.getText().toString()), bicicletaSelecionada.getId(), observacaoPeca.getText().toString());
            repository.add(newPeca);
        }
        finish();
    }
}