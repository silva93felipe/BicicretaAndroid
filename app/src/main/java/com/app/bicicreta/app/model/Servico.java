package com.app.bicicreta.app.model;

import com.app.bicicreta.app.utils.DataUtil;

import java.util.Date;

public class Servico {
    private int id;
    private Date dataServico;
    private double valor;
    private int quilometros;
    private int bicicletaId;
    private String descricao;
    private String modeloBicicleta;
    private String observacao;

    public Servico(String dataServico, double valor,  int bicicletaId, String descricao, String observacao) {
        setDataServico(dataServico);
        setValor(valor);
        setBicicletaId(bicicletaId);
        setDescricao(descricao);
        setObservacao(observacao);
    }

    public Servico(int id, String dataServico, double valor, int quilometros, int bicicletaId, String descricao, String modelo, String observacao) {
        this(dataServico, valor, quilometros, bicicletaId, descricao, observacao);
        this.id = id;
        setModeloBicicleta(modelo);
    }

    public Servico(String dataServico, double valor, int quilometros, int bicicletaId, String descricao, String observacao) {
        this(dataServico, valor, bicicletaId, descricao, observacao);
        setQuilometros(quilometros);
    }

    public int getId() {
        return id;
    }
    public String getDataServico() {
        return DataUtil.DateToUSString(dataServico);
    }
    public void setDataServico(String dataServico) {
        this.dataServico = DataUtil.USStringToDate(dataServico);
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
        if(this.valor < 0 ) this.valor = 0;
    }

    public int getQuilometros() {
        return quilometros;
    }
    public void setQuilometros(int quilometros) {
        this.quilometros = quilometros;
        if(this.quilometros < 0 ) this.quilometros = 0;
    }
    public int getBicicletaId() {
        return bicicletaId;
    }
    public void setBicicletaId(int bicicletaId) {
        this.bicicletaId = bicicletaId;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        if(descricao == null || (descricao != null && descricao.isEmpty())) {
            this.descricao = "SEM NOME";
            return;
        }
        this.descricao = descricao.toUpperCase();
    }

    public String getModeloBicicleta() {
        return modeloBicicleta;
    }

    public void setModeloBicicleta(String modeloBicicleta) {
        if(modeloBicicleta == null || (modeloBicicleta != null && modeloBicicleta.isEmpty())){
            this.modeloBicicleta = "SEM NOME";
            return;
        }
        this.modeloBicicleta = modeloBicicleta.toUpperCase();
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
