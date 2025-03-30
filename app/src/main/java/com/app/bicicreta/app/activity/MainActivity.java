package com.app.bicicreta.app.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.GraficoViagem;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.User;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.UserRepository;
import com.app.bicicreta.app.repository.ViagemRepository;
import com.app.bicicreta.app.service.NotificationLocalService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
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
            dataUltimaViagem, quilometroUltimaViagem, descricaoPecaUltimaCompra,
            dataUltimaCompra, quilometrosUltimaCompra, nadaExibirGraficoViagensTextView, totalViagensTextView, totalPecastextView;
    ImageView mapTabImagemView, toolTabImagemView, bicicletaTabImagemView, configTabImagemView;
    BarChart viagemBarChart;
    ConstraintLayout ultimaViagemConstraintLayout, ultimaPecaCompraConstraintLayout;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        getNomeUsuario();
        getTotalQuilometrosRodados();
        getUltimaViagem();
        getUltimaPecaComprada();
        getTotalPecas();
        getTotalViagens();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getNomeUsuario();
        getTotalQuilometrosRodados();
        getUltimaViagem();
        getUltimaPecaComprada();
        getDadosGraficoViagens();
        criarGrafico();
        getTotalPecas();
        getTotalViagens();
        createNotificationChannel();
    }

    private void inicializarComponentes() {
        totalPecastextView = findViewById(R.id.totalPecasTextView);
        totalViagensTextView = findViewById(R.id.totalViagemTextView);
        nomeUsarioTextView = findViewById(R.id.nomeUsuarioTextView);
        mapTabImagemView = findViewById(R.id.mapTabImagemView);
        mapTabImagemView.setOnClickListener(v -> handleNavigation(ViagensActivity.class));
        configTabImagemView = findViewById(R.id.configTabImagemView);
        configTabImagemView.setOnClickListener(v -> handleNavigation(ConfiguracoesActivity.class));
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
        nadaExibirGraficoViagensTextView = findViewById(R.id.nadaExibirGraficoViagensTextView);
        criarGrafico();
    }

    private void createNotificationChannel() {
        /*
        NotificationLocalService notificationLocalService = new NotificationLocalService(this, MainActivity.class);
        notificationLocalService.createNotification("Saudades", "Porque nos deixou? Estamos com saudades. Volte a pedalar!");
*/
    }

    private List<GraficoViagem> getDadosGraficoViagens(){
        ViagemRepository repository = new ViagemRepository(this);
        return repository.totalViagemPorMes();
    }

    private void criarGrafico(){
        List<GraficoViagem> dados = getDadosGraficoViagens();
        if(!dados.isEmpty()){
            viagemBarChart.setVisibility(View.VISIBLE);
            nadaExibirGraficoViagensTextView.setVisibility(View.GONE);
            viagemBarChart.getAxisRight().setDrawLabels(false);
            YAxis yAxis = viagemBarChart.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisLineWidth(2f);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setLabelCount(10);
            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            float maiorQuantidadeViagens = 0f;
            for (int i = 0; i < dados.size(); i ++){
                int quantidadeViagem = dados.get(i).getQuantidadeViagens();
                if(quantidadeViagem > maiorQuantidadeViagens){
                    maiorQuantidadeViagens = quantidadeViagem;
                }
                entries.add(new BarEntry(i , quantidadeViagem));
                labels.add(dados.get(i).getMes());
            }
            yAxis.setAxisMaximum(maiorQuantidadeViagens + 1);
            BarDataSet dataSet = new BarDataSet(entries, "Meses");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            BarData barData = new BarData(dataSet);
            viagemBarChart.setData(barData);
            Description desc = new Description();
            desc.setText("Viagens / Mês");
            viagemBarChart.setDescription(desc);
            viagemBarChart.getDescription().setPosition(500, 30);
            viagemBarChart.getDescription().setTextSize(14f);
            //viagemBarChart.getDescription().setEnabled(false);
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
            nomeUsarioTextView.setText("DESCONHECIDO!" );
        }else{
            nomeUsarioTextView.setText(user.getNome() );
        }
    }

    private void getTotalViagens(){
        ViagemRepository repository = new ViagemRepository(this);
        totalViagensTextView.setText(String.valueOf(repository.getTotalViagens()));
    }

    private void getTotalPecas(){
        PecaRepository repository = new PecaRepository(this);
        totalPecastextView.setText(String.valueOf(repository.getTotalPecas()));
    }


    private void getTotalQuilometrosRodados(){
        ViagemRepository repository = new ViagemRepository(this);
        quilomentrosRodadosTextView.setText(String.valueOf(repository.totalQuilometrosRodados()));
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