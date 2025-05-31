package com.app.bicicreta.app.model;

import com.app.bicicreta.app.utils.DataUtil;

import java.io.Serializable;
import java.util.Date;

public class Viagem implements Serializable {
    private int id;
    private Date data;
    private int quilometros;
    private String destino;
    private String nomeBicicleta;
    private int bicicletaId;
    private String modeloBicicleta;
    private String observacao;
    public Viagem(String data, int quilometros, String destino, int bicicletaId, String observacao) {
        setData(data);
        setQuilometros(quilometros);
        setDestino(destino.toUpperCase());
        setBicicletaId(bicicletaId);
        setObservacao(observacao);
    }
    public Viagem(int id, String data, int quilometros, String destino, int bicicletaId, String observacao) {
        this(data, quilometros, destino, bicicletaId, observacao);
        this.id = id;
    }
    public Viagem(int id, String data, int quilometros, String destino, int bicicletaId, String modeloBicicleta, String observacao) {
        this(data, quilometros, destino, bicicletaId, observacao);
        this.id = id;
        setModeloBicicleta(modeloBicicleta);
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return DataUtil.DateToUSString(data);
    }

    public void setData(String data) {
        this.data = DataUtil.USStringToDate(data);
    }

    public int getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(int quilometros) {
        this.quilometros = quilometros;
        if(this.quilometros < 0) this.quilometros = 0;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        if(destino == null || (destino != null && destino.isEmpty())) {
            this.destino = "SEM DESTINO";
            return;
        }
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
