package com.app.bicicreta.activity.model;

public class Viagem {
    private String data;
    private String quilometros;
    private String destino;
    private String nomeBicicleta;

    public Viagem() {
    }

    public Viagem(String data, String quilometros, String destino) {
        this.data = data;
        this.quilometros = quilometros;
        this.destino = destino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(String quilometros) {
        this.quilometros = quilometros;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNomeBicicleta() {
        return nomeBicicleta;
    }

    public void setNomeBicicleta(String nomeBicicleta) {
        this.nomeBicicleta = nomeBicicleta;
    }
}
