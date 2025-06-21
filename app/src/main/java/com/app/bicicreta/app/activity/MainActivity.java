package com.app.bicicreta.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.app.bicicreta.R;
import com.app.bicicreta.app.model.GraficoViagem;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.User;
import com.app.bicicreta.app.model.Viagem;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.ServicoRepository;
import com.app.bicicreta.app.repository.UserRepository;
import com.app.bicicreta.app.repository.ViagemRepository;
import com.app.bicicreta.app.service.NotificationLocalService;
import com.app.bicicreta.app.utils.DataUtil;
import com.app.bicicreta.app.utils.MoedaUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView nomeUsarioTextView, quilomentrosRodadosTextView, destinoUltimaViagem,
            dataUltimaViagem, quilometroUltimaViagem, descricaoPecaUltimaCompra,
            dataUltimaCompra, quilometrosUltimaCompra, totalViagensTextView, totalPecastextView, totalServico;
    BarChart viagemBarChart;
    LinearLayout ultimaViagemLinearLayout, ultimaPecaLinearLayout, graficoViagensLinearLayout, linearViagensTab, linearPecaTab, linearBicicletaTab, linearConfiguracaoTab, userLinearLayout;
    private final int DIAS_PARA_NOTIFICAR_AUSENCIA_NO_PEDAL = 5;
    private final int DIAS_PARA_NOTIFICAR_VIAGEM_AGENDADA = 2;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        linearViagensTab = findViewById(R.id.linearViagensTab);
        linearViagensTab.setOnClickListener(v -> handleNavigation(ViagensActivity.class));
        linearConfiguracaoTab = findViewById(R.id.linearConfiguracaoTab);
        linearConfiguracaoTab.setOnClickListener(v -> handleNavigation(ConfiguracoesActivity.class));
        linearPecaTab = findViewById(R.id.linearPecaTab);
        linearPecaTab.setOnClickListener(v -> handleNavigation(PecasEServicosActivity.class));
        linearBicicletaTab = findViewById(R.id.linearBicicletaTab);
        linearBicicletaTab.setOnClickListener(v -> handleNavigation(BicicletasActivity.class));
        userLinearLayout = findViewById(R.id.userLinearLayout);
        userLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ApresentacaoActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        getNomeUsuario();
        getTotalQuilometrosRodados();
        getUltimaViagem();
        getUltimaPecaComprada();
        criarGrafico();
        getTotalPecas();
        getTotalServicos();
        getTotalViagens();
        temViagensAgendadasProxima();
    }

    private void temViagensAgendadasProxima(){
        ViagemRepository repository = new ViagemRepository(this);
        List<Viagem> viagensPendentes = repository.getAllPendentes();
        if(viagensPendentes != null && !viagensPendentes.isEmpty()){
            for (Viagem viagem: viagensPendentes) {
                LocalDate dataBanco = DataUtil.USStringToDate(viagem.getData());
                LocalDate dataAtual = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataAtual = LocalDate.now();
                    long dias = dataAtual.until(dataBanco, ChronoUnit.DAYS);
                    if(dias >= DIAS_PARA_NOTIFICAR_VIAGEM_AGENDADA ){
                        criarNotificacao("", String.format("Você tem uma viagem para %s em %s. É bom se organizar.", viagem.getDestino(), viagem.getData()));
                    }
                }
            }
        }
    }

    private void criarNotificacao(String titulo, String mensagem) {
        NotificationLocalService notificationLocalService = new NotificationLocalService(this, MainActivity.class, this);
        notificationLocalService.createNotification(titulo, mensagem);
    }

    private List<GraficoViagem> getDadosGraficoViagens(){
        ViagemRepository repository = new ViagemRepository(this);
        return repository.totalViagemPorMes();
    }

    private void criarGrafico(){
        graficoViagensLinearLayout = findViewById(R.id.graficoViagensLinearLayout);
        viagemBarChart = findViewById(R.id.chartViagensPorMes);
        List<GraficoViagem> dados = getDadosGraficoViagens();
        if(dados != null && !dados.isEmpty()){
            graficoViagensLinearLayout.setVisibility(View.VISIBLE);
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
        nomeUsarioTextView = findViewById(R.id.nomeUsuarioTextView);
        UserRepository repository = new UserRepository(this);
        user = repository.getOne();
        if(user == null){
            nomeUsarioTextView.setText("DESCONHECIDO!" );
            return;
        }
        String primeiroNome = user.getNome().split(" ")[0];
        nomeUsarioTextView.setText(primeiroNome);
    }

    private void getTotalViagens(){
        ViagemRepository repository = new ViagemRepository(this);
        totalViagensTextView = findViewById(R.id.totalViagemTextView);
        totalViagensTextView.setText(String.valueOf(repository.getTotalViagens()));
    }

    private void getTotalPecas(){
        PecaRepository repository = new PecaRepository(this);
        totalPecastextView = findViewById(R.id.totalPecasTextView);
        totalPecastextView.setText(String.valueOf(repository.getTotalPecas()));
    }

    private void getTotalServicos(){
        ServicoRepository repository = new ServicoRepository(this);
        totalServico = findViewById(R.id.totalServicoTextView);
        totalServico.setText(String.valueOf(repository.getTotalServicos()));
    }

    private void getTotalQuilometrosRodados(){
        ViagemRepository repository = new ViagemRepository(this);
        quilomentrosRodadosTextView = findViewById(R.id.quilometrosRodadosTextView);
        quilomentrosRodadosTextView.setText(String.valueOf(repository.totalQuilometrosRodados()));
    }

    private void getUltimaViagem(){
        ViagemRepository repository = new ViagemRepository(this);
        List<Viagem> viagens  = repository.getLastByParam(1);
        ultimaViagemLinearLayout = findViewById(R.id.ultimaViagemLinearLayout);
        destinoUltimaViagem = findViewById(R.id.destinoUltimaViagemtextView);
        dataUltimaViagem = findViewById(R.id.dataUltimaViagemtextView);
        quilometroUltimaViagem = findViewById(R.id.quilometroUltimaViagemtextView);
        ultimaViagemLinearLayout.setVisibility(View.GONE);
        if(viagens != null && !viagens.isEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            ultimaViagemLinearLayout.setVisibility(View.VISIBLE);
            Viagem viagem = viagens.get(0);
            destinoUltimaViagem.setText(viagem.getDestino());
            dataUltimaViagem.setText(viagem.getData());
            quilometroUltimaViagem.setText(viagem.getQuilometros() + " Km");
            LocalDate dataBanco = DataUtil.USStringToDate(viagem.getData());
            LocalDate dataAtual = LocalDate.now();
            long dias = dataBanco.until(dataAtual, ChronoUnit.DAYS);
            if(dias >= DIAS_PARA_NOTIFICAR_AUSENCIA_NO_PEDAL ){
                criarNotificacao("Saudades", "Faz tempo que você não pedala. Porque não volta a pedalar?");
            }
        }
    }

    private void getUltimaPecaComprada(){
        PecaRepository repository = new PecaRepository(this);
        List<Peca> pecas = repository.getLastByParam(1);
        ultimaPecaLinearLayout = findViewById(R.id.ultimaPecaLinearLayout);
        descricaoPecaUltimaCompra = findViewById(R.id.descricaoPecaUltimaCompraTextView);
        dataUltimaCompra = findViewById(R.id.dataUltimaCompraTextView);
        quilometrosUltimaCompra = findViewById(R.id.quilometroUltimaPecaTextView);
        ultimaPecaLinearLayout.setVisibility(View.GONE);
        if(pecas != null && !pecas.isEmpty()){
            Peca peca = pecas.get(0);
            ultimaPecaLinearLayout.setVisibility(View.VISIBLE);
            descricaoPecaUltimaCompra.setText(peca.getNomePeca());
            dataUltimaCompra.setText(peca.getDataCompra());
            quilometrosUltimaCompra.setText(MoedaUtil.convertToBR(peca.getValor()));
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
        super.onBackPressed();
    }
}