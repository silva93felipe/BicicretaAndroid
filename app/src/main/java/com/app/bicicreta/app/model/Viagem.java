package com.app.bicicreta.app.model;

public class Viagem {
    private int id;
    private String data;
    private int quilometros;
    private String destino;
    private String nomeBicicleta;
    private int bicicletaId;
    public Viagem(String data, int quilometros, String destino) {
        this.data = data;
        this.quilometros = quilometros;
        this.destino = destino;
    }
    public Viagem(int id, String data, int quilometros, String destino) {
        this(data, quilometros, destino);
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(int quilometros) {
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
