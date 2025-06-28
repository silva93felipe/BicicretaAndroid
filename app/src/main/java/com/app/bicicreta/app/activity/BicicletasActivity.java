package com.app.bicicreta.app.activity;

import static android.widget.Toast.LENGTH_LONG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterBicicleta;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.repository.BicicletaRepository;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.ServicoRepository;
import com.app.bicicreta.app.repository.ViagemRepository;

import java.util.ArrayList;
import java.util.List;

public class BicicletasActivity extends AppCompatActivity {
    private Button buttonFormularioBicicleta;
    private RecyclerView bicicletasRecycleView;
    private List<Bicicleta> bicicletas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicicletas);
        iniciarComponentes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarComponentes();
    }

    private void inicializarRecycleView(){
        getAllBicicletas();
        bicicletasRecycleView = findViewById(R.id.bicicletasRecyclerView);
        bicicletasRecycleView.setLayoutManager(new LinearLayoutManager(this));
        bicicletasRecycleView.setHasFixedSize(true);
        AdapterBicicleta adapter = new AdapterBicicleta(bicicletas, new AdapterBicicleta.OnItemClickListener() {
            @Override
            public void deleteItem(Bicicleta item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BicicletasActivity.this);
                builder.setTitle("Atenção").setMessage("Deseja realmente apagar a bicicleta?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(temAlgumRegistroParaEssaBicicleta(item.getId())){
                            Toast.makeText(BicicletasActivity.this, "Existem registros para essa bicicleta e com isso ela não ser apagada.", LENGTH_LONG).show();
                            return;
                        }
                        deleteById(item.getId());
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
            public void editItem(Bicicleta item) {
                handleAtualizarBicicleta(item);
            }
        });
        bicicletasRecycleView.setAdapter(adapter);
    }

    private void iniciarComponentes(){
        inicializarRecycleView();
        buttonFormularioBicicleta = findViewById(R.id.buttonNovaBicicleta);
        buttonFormularioBicicleta.setOnClickListener(v -> handleCadastroBicicleta());
    }

    private void getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(this);
        bicicletas = repository.getAll();
    }

    private boolean temAlgumRegistroParaEssaBicicleta(int id){
        PecaRepository pecaRepository = new PecaRepository(this);
        ViagemRepository viagemRepository = new ViagemRepository(this);
        ServicoRepository servicoRepository = new ServicoRepository(this);
        return pecaRepository.getAllByBicicletaId(id).size() > 0 || viagemRepository.getAllByBicicletaId(id).size() > 0 || servicoRepository.getAllByBicicletaId(id).size() > 0;
    }

    private void deleteById(int id){
        BicicletaRepository repository = new BicicletaRepository(this);
        repository.deleteById(id);
        inicializarRecycleView();
    }

    private void handleAtualizarBicicleta(Bicicleta bicicleta){
        Intent intent = new Intent(BicicletasActivity.this, CadastroBicicletaActivity.class);
        intent.putExtra("bicicleta", bicicleta);
        startActivity(intent);
    }

    private void handleCadastroBicicleta(){
        Intent intent = new Intent(BicicletasActivity.this, CadastroBicicletaActivity.class);
        startActivity(intent);
    }
}