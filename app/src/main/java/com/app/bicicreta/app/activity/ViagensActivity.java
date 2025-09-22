package com.app.bicicreta.app.activity;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterViagem;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.ViagemRepository;
import com.app.bicicreta.app.utils.DataUtil;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViagensActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button novaViagemButton;
    private List<Viagem> viagens = new ArrayList<>();
    private ImageView nadaExibirViagensImagemView, pesquisar;
    private Spinner bicicletaSpinner;
    private EditText datainicialfiltrar, datafinalfiltrar;
    private TabLayout tabViagem;
    private int bicicletaIdSelecionada;
    private boolean somenteViagensPendentes = false;
    private LinearLayout layoutFiltrarViagens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens);
        iniciarComponentes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarComponentes();
    }

    private List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll();
    }

    private boolean temMaisDeUmaBicicleta(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll().size() > 1;
    }

    private void filtrarViagem(){
        if(somenteViagensPendentes){
            filtrarViagensPendentes();
            return;
        }
        filtraViagensPorPeriodo(datainicialfiltrar.getText().toString(), datafinalfiltrar.getText().toString());
    }

    private void filtraViagensPorPeriodo(String datainicio, String datafinal){
        List<String> erros = new ArrayList<>();
        if(datainicio.isEmpty() || datafinal.isEmpty())
            erros.add("A data inicial e final devem está preenchida.");

        if(DataUtil.primeiraDataEhMenorQueASegundaData(datainicio, datafinal))
            erros.add("A data inicial não pode ser maior do que a data final.");

        if(this.bicicletaIdSelecionada <= 0)
            erros.add("Selecione uma bicicleta.");

        if(!erros.isEmpty()){
            Toast.makeText(this, erros.toString(), LENGTH_LONG).show();
            return;
        }
        ViagemRepository repository = new ViagemRepository(this);
        viagens =  repository.getAllByPeridoAnBicicletaId(datainicio, datafinal, bicicletaIdSelecionada);
        inicializarRecycleView(viagens);
    }

    private void filtrarViagensPendentes(){
        List<String> erros = new ArrayList<>();
        if(this.bicicletaIdSelecionada <= 0)
            erros.add("Selecione uma bicicleta.");

        if(!erros.isEmpty()){
            Toast.makeText(this, erros.toString(), LENGTH_LONG).show();
            return;
        }
        ViagemRepository repository = new ViagemRepository(this);
        viagens =  repository.getAllPendentesByBicicletaId(bicicletaIdSelecionada);
        inicializarRecycleView(viagens);
    }


    private void iniciarTab(){
        tabViagem = findViewById(R.id.tabLayout);
        tabViagem.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0:
                        somenteViagensPendentes = false;
                        layoutFiltrarViagens.setVisibility(View.VISIBLE);
                        iniciarSpinnerBicicleta();
                        inicializarRecycleView(new ArrayList<Viagem>());
                        break;
                    case 1:
                        somenteViagensPendentes = true;
                        layoutFiltrarViagens.setVisibility(View.GONE);
                        iniciarSpinnerBicicleta();
                        inicializarRecycleView(new ArrayList<Viagem>());
                        break;
                    default:
                        somenteViagensPendentes = false;
                        inicializarRecycleView(new ArrayList<Viagem>());
                        iniciarSpinnerBicicleta();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void iniciarSpinnerBicicleta(){
        bicicletaSpinner = findViewById(R.id.bicicletaSpinnerViagem);
        List<ItemSpinner> itemList = new ArrayList<>();
        if(temMaisDeUmaBicicleta()){
            itemList.add(new ItemSpinner(0, "Selecione uma bicicleta"));
        }
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<ItemSpinner>(this, android.R.layout.simple_spinner_item, itemList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0 && temMaisDeUmaBicicleta();
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0 && temMaisDeUmaBicicleta()){
                    tv.setTextColor(Color.GRAY);
                }else {
                    tv.setTextColor(Color.YELLOW);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bicicletaSpinner.setAdapter(adapter);
        bicicletaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datainicialfiltrar.setText("");
                datafinalfiltrar.setText("");
                ItemSpinner item = (ItemSpinner)parent.getItemAtPosition(position);
                int bicicletaId = item.getId();
                if(bicicletaId > 0){
                    bicicletaIdSelecionada = bicicletaId;
                    if(somenteViagensPendentes)
                        filtrarViagensPendentes();
                    else{
                        filtraViagensPorPeriodo(inicioDoMes()[0], inicioDoMes()[1]);
                        inicializarRecycleView(viagens);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String[] inicioDoMes(){
        String dataInicial = "0000-00-00 00:00:00";
        String dataFinal = "0000-00-00 00:00:00";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate primeiroDiaDoMes = LocalDate.now().withDayOfMonth(1);
            LocalDate ultimoDiaDoMes = LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dataInicial = primeiroDiaDoMes.format(format);
            dataFinal = ultimoDiaDoMes.format(format);
        }
        return new String[]{ dataInicial, dataFinal };
    }

    private void iniciarComponentes(){
        iniciarSpinnerBicicleta();
        iniciarCalendario();
        iniciarTab();
        layoutFiltrarViagens = findViewById(R.id.layoutFiltrarViagens);
        pesquisar = findViewById(R.id.pesquisar);
        pesquisar.setOnClickListener(v -> filtrarViagem());
        novaViagemButton = findViewById(R.id.buttonNovaViagem);
        novaViagemButton.setOnClickListener(v -> handleCadastroViagem());
    }

    private void iniciarCalendario(){
        datainicialfiltrar = findViewById(R.id.datainicialfiltrar);
        datainicialfiltrar.setOnClickListener( v ->  {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        datainicialfiltrar.setText(selectedDate);
                    },year, month, day
            );
            datePicker.show();
        });

        datafinalfiltrar = findViewById(R.id.datafinalfiltrar);
        datafinalfiltrar.setOnClickListener( v ->  {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        datafinalfiltrar.setText(selectedDate);
                    },year, month, day
            );
            datePicker.show();
        });
    }

    private void exibirMessageListaVazia(){
        nadaExibirViagensImagemView = findViewById(R.id.nadaExibirViagensImageView);
        if(viagens.isEmpty()){
            nadaExibirViagensImagemView.setVisibility(View.VISIBLE);
        }else{
            nadaExibirViagensImagemView.setVisibility(View.GONE);
        }
    }

    private void inicializarRecycleView(List<Viagem> viagens){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewViagens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterViagem adapter = new AdapterViagem(viagens, new AdapterViagem.OnItemClickListener() {
            @Override
            public void deleteItem(Viagem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViagensActivity.this);
                builder.setTitle("Atenção").setMessage("Deseja realmente apagar a viagem?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteViagem(item.getId());
                        iniciarComponentes();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void editItem(Viagem item) {
                handleEditarViagem(item);
            }
        });
        recyclerView.setAdapter(adapter);
        exibirMessageListaVazia();
    }

    private void handleEditarViagem(Viagem viagem){
        Intent cadastroViagemIntent = new Intent(ViagensActivity.this, CadastroViagemActivity.class);
        cadastroViagemIntent.putExtra("viagem", viagem);
        inicializarRecycleView(new ArrayList<Viagem>());
        startActivity(cadastroViagemIntent);
    }

    private void handleCadastroViagem(){;
        Intent cadastroViagemIntent = new Intent(ViagensActivity.this, CadastroViagemActivity.class);
        inicializarRecycleView(new ArrayList<Viagem>());
        startActivity(cadastroViagemIntent);
    }

    private void deleteViagem(int id){
        ViagemRepository repository = new ViagemRepository(this);
        inicializarRecycleView(new ArrayList<Viagem>());
        repository.deleteById(id);
    }
}