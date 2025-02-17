package com.app.bicicreta.app.model;

public class GraficoViagem {
    private int quantidadeViagens;
    private String mes;

    public GraficoViagem(int quantidadeViagens, String mes) {
        this.quantidadeViagens = quantidadeViagens;
        this.mes = mes;
    }

    public int getQuantidadeViagens() {
        return quantidadeViagens;
    }

    public void setQuantidadeViagens(int quantidadeViagens) {
        this.quantidadeViagens = quantidadeViagens;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
