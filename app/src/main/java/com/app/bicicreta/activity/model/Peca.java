package com.app.bicicreta.activity.model;

public class Peca {
    private String dataCompra;
    private String valor;
    private String quilometros;
    private String nomeBicicleta;
    private String nomePeca;

    public Peca() {
    }

    public Peca(String dataCompra, String valor, String quilometros, String nomeBicicleta) {
        this.dataCompra = dataCompra;
        this.valor = valor;
        this.quilometros = quilometros;
        this.nomeBicicleta = nomeBicicleta;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(String quilometros) {
        this.quilometros = quilometros;
    }

    public String getNomeBicicleta() {
        return nomeBicicleta;
    }

    public void setNomeBicicleta(String nomeBicicleta) {
        this.nomeBicicleta = nomeBicicleta;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }
}
