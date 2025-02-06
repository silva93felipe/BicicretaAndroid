package com.app.bicicreta.app.model;

public class Peca {
    private int id;
    private String dataCompra;
    private double valor;
    private int quilometros;
    private Bicicleta bicicleta;
    private String nomePeca;
    public Peca(String descricao, String dataCompra, double valor, int bicicletaId) {
        this.nomePeca = descricao;
        this.dataCompra = dataCompra;
        this.valor = valor;
        this.quilometros = 0;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
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

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
