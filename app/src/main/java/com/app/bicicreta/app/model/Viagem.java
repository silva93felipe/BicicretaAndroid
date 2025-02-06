package com.app.bicicreta.app.model;

public class Viagem {
    private int id;
    private String data;
    private String quilometros;
    private String destino;
    private String nomeBicicleta;
    private int bicicletaId;
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

    public int getBicicletaId() {
        return bicicletaId;
    }

    public void setBicicletaId(int bicicletaId) {
        this.bicicletaId = bicicletaId;
    }

    public int getId() {
        return id;
    }
}
