package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterViagem;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.ViagemRepository;

import java.util.ArrayList;
import java.util.List;

public class ViagensActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button novaViagemButton;
    private List<Viagem> viagens = new ArrayList<>();
    private ImageView nadaExibirViagensImagemView;
    private Spinner bicicletaSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens);
        iniciarComponentes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inicializarRecycleView();
    }

    private void getAllByBicicletaId(int id){
        ViagemRepository repository = new ViagemRepository(this);
        viagens = repository.getAllByBicicletaId(id);
    }

    private List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        return repository.getAll();
    }

    private void iniciarSpinnerBicicleta(){
        bicicletaSpinner = findViewById(R.id.bicicletaSpinnerViagem);
        List<ItemSpinner> itemList = new ArrayList<>();
        itemList.add(new ItemSpinner(0, "Selecione uma bicicleta"));
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<ItemSpinner>(this, android.R.layout.simple_spinner_item, itemList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
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
                ItemSpinner item = (ItemSpinner)parent.getItemAtPosition(position);
                if(position > 0){
                    int bicicletaId = item.getId();
                    getAllByBicicletaId(bicicletaId);
                    inicializarRecycleView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void iniciarComponentes(){
        inicializarRecycleView();
        iniciarSpinnerBicicleta();
        novaViagemButton = findViewById(R.id.buttonNovaViagem);
        novaViagemButton.setOnClickListener(v -> handleCadastroViagem());
    }

    private void exibirMessageListaVazia(){
        nadaExibirViagensImagemView = findViewById(R.id.nadaExibirViagensImageView);
        if(viagens.isEmpty()){
            nadaExibirViagensImagemView.setVisibility(View.VISIBLE);
        }else{
            nadaExibirViagensImagemView.setVisibility(View.GONE);
        }
    }

    private void inicializarRecycleView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewViagens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        AdapterViagem adapter = new AdapterViagem(viagens, v -> {
            handleEditarViagem(v);
        });
        recyclerView.setAdapter(adapter);
        exibirMessageListaVazia();
    }

    private void handleEditarViagem(Viagem v){
        Intent cadastroViagemIntent = new Intent(ViagensActivity.this, CadastroViagemActivity.class);
        cadastroViagemIntent.putExtra("viagem", v);
        startActivity(cadastroViagemIntent);
    }

    private void handleCadastroViagem(){
        Intent cadastroViagemIntent = new Intent(ViagensActivity.this, CadastroViagemActivity.class);
        startActivity(cadastroViagemIntent);
    }
}