package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.ViagemRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroViagemActivity extends AppCompatActivity {
    private EditText dataCadastroViagemEditText, destinoCadastroViagemEditText, quilometrosCadastroViagemEditText, observacao, origemCadastroViagemEditText;
    private Spinner bicicletaCadastroPecaSpinner;
    private Viagem viagemEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagem);
        inicializarComponentes();
        viagemEdit = (Viagem) getIntent().getSerializableExtra("viagem");
        if(viagemEdit != null){
            destinoCadastroViagemEditText.setText(viagemEdit.getDestino());
            quilometrosCadastroViagemEditText.setText(String.valueOf(viagemEdit.getQuilometros()));
            dataCadastroViagemEditText.setText(viagemEdit.getData());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void iniciarCalendario(){
        dataCadastroViagemEditText = findViewById(R.id.dataCadastroViagemEditText);
        dataCadastroViagemEditText.setOnClickListener( v ->  {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    dataCadastroViagemEditText.setText(selectedDate);
                },year, month, day
        );
        datePicker.show();
        });
    }

    private void iniciarSpinnerBicicleta(){
        bicicletaCadastroPecaSpinner = findViewById(R.id.bicicletaCadastroPecaSpinner);
        List<ItemSpinner> itemList = new ArrayList<>();
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bicicletaCadastroPecaSpinner.setAdapter(adapter);
    }


    private void inicializarComponentes(){
        iniciarCalendario();
        iniciarSpinnerBicicleta();
        observacao = findViewById(R.id.editTextObservacaoViagem);
        origemCadastroViagemEditText = findViewById(R.id.origemCadastroViagemEditText);
        destinoCadastroViagemEditText = findViewById(R.id.destinoCadastroViagemEditText);
        quilometrosCadastroViagemEditText = findViewById(R.id.quilometrosCadastroViagemEditText);
        Button buttonAdicionarPeca = findViewById(R.id.buttonAdicionarViagem);
        buttonAdicionarPeca.setOnClickListener(v -> handleCadastrarViagem());
    }

    private List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll();
    }

    private void handleCadastrarViagem(){
        List<String> erros = new ArrayList<>();
        if(destinoCadastroViagemEditText.getText().toString().trim().isEmpty()){
            erros.add("Destino");
        }

        if(quilometrosCadastroViagemEditText.getText().toString().trim().isEmpty()){
            erros.add("Quilometros");
        }

        if(dataCadastroViagemEditText.getText().toString().trim().isEmpty()){
            erros.add("Data");
        }

        if(bicicletaCadastroPecaSpinner.getSelectedItem().toString().trim().isEmpty()){
            erros.add("Bicicleta");
        }

        if(origemCadastroViagemEditText.getText().toString().trim().isEmpty()){
            erros.add("Origem");
        }

        if(!erros.isEmpty()){
            Toast.makeText(this, "Preencha os seguintes campos: " + erros, Toast.LENGTH_LONG).show();
            return;
        }

        ItemSpinner bicicletaSelecionada = (ItemSpinner) bicicletaCadastroPecaSpinner.getSelectedItem();

        if(viagemEdit != null){
            Viagem newViagem = new Viagem(viagemEdit.getId(), dataCadastroViagemEditText.getText().toString(),
                    Integer.parseInt(quilometrosCadastroViagemEditText.getText().toString()),
                    destinoCadastroViagemEditText.getText().toString(),
                    bicicletaSelecionada.getId(), observacao.getText().toString(), origemCadastroViagemEditText.getText().toString());
            atualizarViagem(newViagem);
        }else{
            Viagem newViagem = new Viagem(dataCadastroViagemEditText.getText().toString(),
                    Integer.parseInt(quilometrosCadastroViagemEditText.getText().toString()),
                    destinoCadastroViagemEditText.getText().toString(),
                    bicicletaSelecionada.getId(), observacao.getText().toString(), origemCadastroViagemEditText.getText().toString());
            saveViagem(newViagem);
            atualizarQuilometrosBicicleta(bicicletaSelecionada.getId(), Integer.parseInt(quilometrosCadastroViagemEditText.getText().toString()));
            atualizarQuilometrosPeca(bicicletaSelecionada.getId(), Integer.parseInt(quilometrosCadastroViagemEditText.getText().toString()));
        }
        finish();
    }
    private void saveViagem(Viagem newViagem){
        ViagemRepository repository = new ViagemRepository(this);
        repository.add(newViagem);
    }

    private void atualizarViagem(Viagem viagem){
        ViagemRepository repository = new ViagemRepository(this);
        repository.update(viagem);
    }

    private void atualizarQuilometrosBicicleta(int bicicletaId, int quilometros){
        BicicletaRepository repository = new BicicletaRepository(this);
        repository.updateQuilometrosRodadosByBicicletaId(bicicletaId, quilometros);
    }

    private void atualizarQuilometrosPeca(int bicicletaId, int quilometros){
        PecaRepository repository = new PecaRepository(this);
        repository.updateQuilometrosRodadosByBicicletaId(bicicletaId, quilometros);
    }
}