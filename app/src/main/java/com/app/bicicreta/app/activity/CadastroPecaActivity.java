package com.app.bicicreta.app.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.repository.BicicletaRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroPecaActivity extends AppCompatActivity {
    private EditText dataCompraPeca, descricaoPeca, valorPeca;
    private Spinner bicicletaSpinner;
    private Button botaoSalvarPeca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_peca);
        inicializarComponentes();
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
                        String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                        dataCompraPeca.setText(selectedDate);
                    },year, month, day
            );
            datePicker.show();
        });
        descricaoPeca = findViewById(R.id.descricaoPecaEditText);
        valorPeca = findViewById(R.id.valorPecaEditText);
        botaoSalvarPeca = findViewById(R.id.buttonAdicionarPeca);
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
    }

}