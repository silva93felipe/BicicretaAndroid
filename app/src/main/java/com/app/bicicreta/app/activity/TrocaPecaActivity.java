package com.app.bicicreta.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.adapter.AdapterTroca;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Troca;
import com.app.bicicreta.app.repository.TrocaRepository;

import java.util.ArrayList;
import java.util.List;

public class TrocaPecaActivity extends AppCompatActivity {
    private TextView  tituloHeader;
    private Button buttonNovaTroca;
    private RecyclerView listaTrocaRecycleView;
    private List<Troca> trocas = new ArrayList<>();
    private View nadaExibirImagemView, headerTroca;
    private Peca pecaDto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_peca);
        pecaDto = (Peca) getIntent().getSerializableExtra("peca");
        iniciarComponentes();
        if(pecaDto != null){
            tituloHeader.setText(String.format("%s - %s Km", pecaDto.getNomePeca(), pecaDto.getQuilometros()));
        }
    }

    private void iniciarComponentes(){
        tituloHeader = findViewById(R.id.headertextView);
        buttonNovaTroca = findViewById(R.id.buttonNovaTroca);
        buttonNovaTroca.setOnClickListener(v -> handleClick(pecaDto));
        trocas = getAllByPecaId(pecaDto.getId());
        inicializarRecycleView(trocas);

    }

    private List<Troca> getAllByPecaId(int pecaId){
        TrocaRepository repository = new TrocaRepository(this);
        return repository.getAllByPecaId(pecaId);
    }

    private void delete(int id){
        TrocaRepository repository = new TrocaRepository(this);
        repository.deleteById(id);
    }

    private void trocar(Peca peca){
        TrocaRepository repository = new TrocaRepository(this);
        Troca troca = new Troca(peca.getQuilometros(), peca.getId());
        repository.add(troca);
    }

    private void handleClick(Peca peca){
        AlertDialog.Builder builder = new AlertDialog.Builder(TrocaPecaActivity.this);
        builder.setTitle("Atenção").setMessage("Deseja realmente trocar peça?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                trocar(peca);
                iniciarComponentes();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void inicializarRecycleView(List<Troca> trocas){
        listaTrocaRecycleView = (RecyclerView)findViewById(R.id.listaTrocaRecycleView);
        listaTrocaRecycleView.setLayoutManager(new LinearLayoutManager(this));
        listaTrocaRecycleView.setHasFixedSize(true);
        AdapterTroca adapter = new AdapterTroca(trocas, new AdapterTroca.OnItemClickListener() {
            @Override
            public void deleteItem(Troca item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TrocaPecaActivity.this);
                builder.setTitle("Atenção").setMessage("Deseja realmente apagar a troca de peça?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete(item.getId());
                        iniciarComponentes();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        listaTrocaRecycleView.setAdapter(adapter);
        exibirMessageListaVazia();
    }

    private void exibirMessageListaVazia(){
        nadaExibirImagemView = findViewById(R.id.nadaExibirTroca);
        if(trocas.isEmpty()){
            nadaExibirImagemView.setVisibility(View.VISIBLE);
        }else{
            nadaExibirImagemView.setVisibility(View.GONE);
        }
    }
}