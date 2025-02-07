package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.repository.BicicletaRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroViagemActivity extends AppCompatActivity {
    private EditText dataCadastroViagemEditText;
    private Spinner bicicletaCadastroPecaSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagem);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        dataCadastroViagemEditText = findViewById(R.id.dataCadastroViagemEditText);
        dataCadastroViagemEditText.setOnClickListener( v ->  {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    dataCadastroViagemEditText.setText(selectedDate);
                },year, month, day
            );
            datePicker.show();
        });
        bicicletaCadastroPecaSpinner = findViewById(R.id.bicicletaCadastroPecaSpinner);
        List<ItemSpinner> itemList = new ArrayList<>();
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bicicletaCadastroPecaSpinner.setAdapter(adapter);
    }

    public List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll();
    }
}