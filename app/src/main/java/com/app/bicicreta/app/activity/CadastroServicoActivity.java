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
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Servico;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.ServicoRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class CadastroServicoActivity extends AppCompatActivity {

    private EditText dataServico, descricaoServico, valorServico;
    private Spinner bicicletaSpinner;
    private Button botaoSalvar;
    private Servico servicoEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);
        inicializarComponentes();
        servicoEdit = (Servico) getIntent().getSerializableExtra("servico");
        if(servicoEdit != null){
            descricaoServico.setText(servicoEdit.getDescricao());
            valorServico.setText(String.valueOf(servicoEdit.getValor()));
            dataServico.setText(servicoEdit.getDataServico());
        }
    }

    private void inicializarComponentes(){
        descricaoServico = findViewById(R.id.descricaoServicoEditText);
        valorServico = findViewById(R.id.valorServicoEditText);
        dataServico = findViewById(R.id.dataServicoEditText);
        dataServico.setOnClickListener( v ->  {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        dataServico.setText(selectedDate);
                    },year, month, day
            );
            datePicker.show();
        });

        botaoSalvar = findViewById(R.id.buttonAdicionarServico);
        botaoSalvar.setOnClickListener(v -> createServico());
        bicicletaSpinner = findViewById(R.id.bicicletaIdServicoSpinner);
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

    private void createServico(){
        List<String> erros = new ArrayList<>();
        if(descricaoServico.getText().toString().trim().isEmpty()){
            erros.add("Descrição");
        }

        if(valorServico.getText().toString().trim().isEmpty()){
            erros.add("Valor");
        }

        if(dataServico.getText().toString().trim().isEmpty()){
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
        ServicoRepository repository = new ServicoRepository(this);
        Bicicleta bicicleta = getAllBicicletas().stream().filter(e -> e.getId() == bicicletaSelecionada.getId()).findFirst().get();
        if(servicoEdit != null){
            Servico newServico = new Servico(servicoEdit.getId(), dataServico.getText().toString(), Double.parseDouble(valorServico.getText().toString()),
                    servicoEdit.getQuilometros(), bicicletaSelecionada.getId(), descricaoServico.getText().toString());
            repository.update(newServico);
        }else{
            Servico newServico = new Servico(dataServico.getText().toString(), Double.parseDouble(valorServico.getText().toString()), bicicleta.getQuilometrosRodados(), bicicletaSelecionada.getId(), descricaoServico.getText().toString());
            repository.add(newServico);
        }
        finish();
    }
}