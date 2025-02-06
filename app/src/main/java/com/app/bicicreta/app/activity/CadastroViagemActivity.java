package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.app.bicicreta.R;

import java.util.Calendar;

public class CadastroViagemActivity extends AppCompatActivity {
    private EditText dataCadastroViagemEditText;
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
    }
}