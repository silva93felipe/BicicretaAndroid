package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
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

public class MainActivity extends AppCompatActivity {
    TextView nomeUsarioTextView, quilomentrosRodadosTextView, destinoUltimaViagem,
            dataUltimaViagem, quilometroUltimaViagem, descricaoPecaUltimaCompra, dataUltimaCompra, quilometrosUltimaCompra;
    RecyclerView ultimasViagensRecyclerView;
    ImageView mapTabImagemView, toolTabImagemView, bicicletaTabImagemView;
    BarChart viagemBarChart;
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
        criarGrafico();
    }

    private void criarGrafico(){
        viagemBarChart.getAxisRight().setDrawLabels(false);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0 , 45f));
        entries.add(new BarEntry(1 , 80f));
        entries.add(new BarEntry(2 , 65f));
        entries.add(new BarEntry(3 , 38f));
        entries.add(new BarEntry(4 , 55f));

        YAxis yAxis = viagemBarChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet dataSet = new BarDataSet(entries, "Sub");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        viagemBarChart.setData(barData);

        viagemBarChart.getDescription().setEnabled(false);
        viagemBarChart.invalidate();

        viagemBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Arrays.asList("Maths", "Science", "English", "IT")));
        viagemBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        viagemBarChart.getXAxis().setGranularity(1f);
        viagemBarChart.getXAxis().setGranularityEnabled(true);
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
        Viagem viagem = repository.getLastByParam(1).get(0);
        if(viagem != null){
            destinoUltimaViagem.setText(viagem.getDestino());
            dataUltimaViagem.setText(viagem.getData());
            quilometroUltimaViagem.setText(viagem.getQuilometros() + " Km");
        }
    }

    private void getUltimaPecaComprada(){
        PecaRepository repository = new PecaRepository(this);
        Peca peca = repository.getLastByParam(1).get(0);
        if(peca != null){
            descricaoPecaUltimaCompra.setText(peca.getNomePeca());
            dataUltimaCompra.setText(peca.getDataCompra());
            quilometrosUltimaCompra.setText(peca.getQuilometros() + " Km");
        }
    }
}