package com.app.bicicreta.app.model;

public class Viagem {
    private int id;
    private String data;
    private int quilometros;
    private String destino;
    private String nomeBicicleta;
    private int bicicletaId;
    private String modeloBicicleta;
    public Viagem(String data, int quilometros, String destino, int bicicletaId) {
        this.data = data;
        this.quilometros = quilometros;
        this.destino = destino.toUpperCase();
        this.bicicletaId = bicicletaId;
    }
    public Viagem(int id, String data, int quilometros, String destino, int bicicletaId) {
        this(data, quilometros, destino, bicicletaId);
        this.id = id;
    }
    public Viagem(int id, String data, int quilometros, String destino, int bicicletaId, String modeloBicicleta) {
        this(data, quilometros, destino, bicicletaId);
        this.id = id;
        this.modeloBicicleta = modeloBicicleta;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public int getBicicletaId() {
        return bicicletaId;
    }
    public void setBicicletaId(int bicicletaId) {
        this.bicicletaId = bicicletaId;
    }
    public String getModeloBicicleta() {
        return modeloBicicleta;
    }
    public void setModeloBicicleta(String modeloBicicleta) {
        this.modeloBicicleta = modeloBicicleta;
    }

}
