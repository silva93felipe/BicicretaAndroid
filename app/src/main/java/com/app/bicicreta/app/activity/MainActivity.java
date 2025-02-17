package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.GraficoViagem;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.User;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.UserRepository;
import com.app.bicicreta.app.repository.ViagemRepository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView nomeUsarioTextView, quilomentrosRodadosTextView, destinoUltimaViagem,
            dataUltimaViagem, quilometroUltimaViagem, descricaoPecaUltimaCompra, dataUltimaCompra, quilometrosUltimaCompra;
    ImageView mapTabImagemView, toolTabImagemView, bicicletaTabImagemView;
    BarChart viagemBarChart;
    ConstraintLayout ultimaViagemConstraintLayout, ultimaPecaCompraConstraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        getNomeUsuario();
        getTotalQuilometrosRodados();
        getUltimaViagem();
        getUltimaPecaComprada();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getTotalQuilometrosRodados();
        getUltimaViagem();
        getUltimaPecaComprada();
    }

    private void inicializarComponentes(){
        nomeUsarioTextView = findViewById(R.id.nomeUsuarioTextView);
        mapTabImagemView = findViewById(R.id.mapTabImagemView);
        mapTabImagemView.setOnClickListener(v -> handleNavigation(ViagensActivity.class));
        toolTabImagemView = findViewById(R.id.toolTabImagemView);
        toolTabImagemView.setOnClickListener(v -> handleNavigation(PecasActivity.class));
        quilomentrosRodadosTextView = findViewById(R.id.quilometrosRodadosTextView);
        bicicletaTabImagemView = findViewById(R.id.bicicletaTabImagemView);
        bicicletaTabImagemView.setOnClickListener(v -> handleNavigation(BicicletasActivity.class));
        destinoUltimaViagem = findViewById(R.id.destinoUltimaViagemtextView);
        dataUltimaViagem = findViewById(R.id.dataUltimaViagemtextView);
        quilometroUltimaViagem = findViewById(R.id.quilometroUltimaViagemtextView);
        descricaoPecaUltimaCompra = findViewById(R.id.descricaoPecaUltimaCompraTextView);
        dataUltimaCompra = findViewById(R.id.dataUltimaCompraTextView);
        quilometrosUltimaCompra = findViewById(R.id.quilometroUltimaPecaTextView);
        viagemBarChart = findViewById(R.id.chartViagensPorMes);
        ultimaViagemConstraintLayout = findViewById(R.id.ultimaViagemConstraintLayout);
        ultimaPecaCompraConstraintLayout = findViewById(R.id.ultimaPecaCompradaConstraintLayout);

        criarGrafico();
    }

    private List<GraficoViagem> getDadosGraficoViagens(){
        ViagemRepository repository = new ViagemRepository(this);
        return repository.totalViagemPorMes();
    }

    private void criarGrafico(){
        viagemBarChart.getAxisRight().setDrawLabels(false);
        List<GraficoViagem> dados = getDadosGraficoViagens();
        if(!dados.isEmpty()){
            YAxis yAxis = viagemBarChart.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisLineWidth(2f);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setLabelCount(10);

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            for (int i = 0; i < dados.size(); i ++){
                entries.add(new BarEntry(i , dados.get(i).getQuantidadeViagens()));
                labels.add(dados.get(i).getMes());
            }

            BarDataSet dataSet = new BarDataSet(entries, "Meses");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            BarData barData = new BarData(dataSet);
            viagemBarChart.setData(barData);

            viagemBarChart.getDescription().setEnabled(false);
            viagemBarChart.invalidate();
            viagemBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
            viagemBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            viagemBarChart.getXAxis().setGranularity(1f);
            viagemBarChart.getXAxis().setGranularityEnabled(true);
        }
    }

    private void handleNavigation(Class activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    private void getNomeUsuario(){
        UserRepository repository = new UserRepository(this);
        User user = repository.getOne();
        if(user == null){
            nomeUsarioTextView.setText("Olá, DESCONHECIDO!" );
        }else{
            nomeUsarioTextView.setText("Olá, " + user.getNome() );
        }
    }

    private void getTotalQuilometrosRodados(){
        ViagemRepository repository = new ViagemRepository(this);
        quilomentrosRodadosTextView.setText(repository.totalQuilometrosRodados() + " Km");
    }

    private void getUltimaViagem(){
        ViagemRepository repository = new ViagemRepository(this);
        List<Viagem> viagens  = repository.getLastByParam(1);
        if(viagens != null && !viagens.isEmpty()){
            ultimaViagemConstraintLayout.setVisibility(View.VISIBLE);
            Viagem viagem = viagens.get(0);
            destinoUltimaViagem.setText(viagem.getDestino());
            dataUltimaViagem.setText(viagem.getData());
            quilometroUltimaViagem.setText(viagem.getQuilometros() + " Km");
        }else{
            ultimaViagemConstraintLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void getUltimaPecaComprada(){
        PecaRepository repository = new PecaRepository(this);
        List<Peca> pecas = repository.getLastByParam(1);
        if(pecas != null && !pecas.isEmpty()){
            Peca peca = pecas.get(0);
            ultimaPecaCompraConstraintLayout.setVisibility(View.VISIBLE);
            descricaoPecaUltimaCompra.setText(peca.getNomePeca());
            dataUltimaCompra.setText(peca.getDataCompra());
            quilometrosUltimaCompra.setText(peca.getQuilometros() + " Km");
        }else{
            ultimaPecaCompraConstraintLayout.setVisibility(View.INVISIBLE);
        }
    }
}