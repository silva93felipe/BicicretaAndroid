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

    public Servico(int id, String dataServico, double valor, int quilometros, int bicicletaId, String descricao, String modeloBicicleta) {
        this.id = id;
        this.dataServico = DataUtil.USStringToDate(dataServico);
        this.valor = valor;
        this.quilometros = quilometros;
        this.bicicletaId = bicicletaId;
        this.descricao = descricao;
        this.modeloBicicleta = modeloBicicleta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    }

    public int getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(int quilometros) {
        this.quilometros = quilometros;
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
        this.descricao = descricao;
    }

    public String getModeloBicicleta() {
        return modeloBicicleta;
    }

    public void setModeloBicicleta(String modeloBicicleta) {
        this.modeloBicicleta = modeloBicicleta;
    }
}
