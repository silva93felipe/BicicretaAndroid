package com.app.bicicreta.app.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.model.Servico;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.ServicoRepository;
import com.app.bicicreta.app.utils.MoedaUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroServicoActivity extends AppCompatActivity {
    private EditText dataServico, descricaoServico, valorServico, observacao, quilometroServicoEditText;
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
            valorServico.setText(MoedaUtil.convertToBR(servicoEdit.getValor()));
            dataServico.setText(servicoEdit.getDataServico());
            observacao.setText(servicoEdit.getObservacao());
            quilometroServicoEditText.setText(String.valueOf(servicoEdit.getQuilometros()));
        }
    }

    public boolean temMaisDeUmaBicicleta(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll().size() > 1;
    }

    private void iniciarSpinnerBicicleta(){
        bicicletaSpinner = findViewById(R.id.bicicletaIdServicoSpinner);
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

    private void iniciarCalendario(){
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
    }

    private void inicializarComponentes(){
        descricaoServico = findViewById(R.id.descricaoServicoEditText);
        valorServico = findViewById(R.id.valorServicoEditText);
        dataServico = findViewById(R.id.dataServicoEditText);
        observacao = findViewById(R.id.editTextObservacaoServico);
        botaoSalvar = findViewById(R.id.buttonAdicionarServico);
        quilometroServicoEditText = findViewById(R.id.quilometroServicoEditText);
        botaoSalvar.setOnClickListener(v -> createServico());
        iniciarSpinnerBicicleta();
        iniciarCalendario();
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
        String quilometrosView =  String.valueOf(quilometroServicoEditText.getText());
        int quilometros = 0;
        if(quilometrosView != null && !quilometrosView.isEmpty())
            quilometros = Integer.parseInt(quilometrosView);
        if(servicoEdit != null){
            Servico newServico = new Servico(servicoEdit.getId(), dataServico.getText().toString(), Double.parseDouble(valorServico.getText().toString()),
                    quilometros, bicicletaSelecionada.getId(), descricaoServico.getText().toString(), servicoEdit.getModeloBicicleta(), observacao.getText().toString());
            repository.update(newServico);
        }else{
            Servico newServico = new Servico(dataServico.getText().toString(), Double.parseDouble(valorServico.getText().toString()), quilometros, bicicletaSelecionada.getId(), descricaoServico.getText().toString(), observacao.getText().toString());
            repository.add(newServico);
        }
        finish();
    }
}