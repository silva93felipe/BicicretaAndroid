package com.app.bicicreta.app.model;

import com.app.bicicreta.app.utils.DataUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Peca implements Serializable {
    private int id;
    private LocalDate dataCompra;
    private double valor;
    private int quilometros;
    private int bicicletaId;
    private String nomePeca;
    private String modeloBicicleta;
    private String observacao;
    public Peca(String descricao, String dataCompra, double valor, int bicicletaId, String observacao) {
        setNomePeca(descricao.toUpperCase());
        setDataCompra(dataCompra);
        setValor(valor);
        setBicicletaId(bicicletaId);
        setObservacao(observacao);
    }

    public Peca(int id, String descricao, String dataCompra, double valor, int quilometros, int bicicletaId, String observacao) {
        this(descricao, dataCompra, valor, bicicletaId, observacao);
        this.id = id;
        setQuilometros(quilometros);
    }

    public Peca(int id, String descricao, String dataCompra, double valor, int quilometros, int bicicletaId, String modeloBicicleta, String observacao) {
        this(id, descricao, dataCompra, valor, quilometros, bicicletaId, observacao);
        setModeloBicicleta(modeloBicicleta);
    }

    public String getDataCompra() {
        return DataUtil.DateToUSString(dataCompra);
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = DataUtil.USStringToDate(dataCompra);
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
        if(this.quilometros < 0) this.quilometros = 0;
    }

    public int getBicicletaId() {
        return bicicletaId;
    }

    public void setBicicletaId(int bicicletaId) {
        this.bicicletaId = bicicletaId;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        if(nomePeca == null || ( nomePeca != null && nomePeca.isEmpty())){
            this.nomePeca = "SEM NOME";
            return;
        }
        this.nomePeca = nomePeca.toUpperCase();
    }

    public int getId() {
        return id;
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
