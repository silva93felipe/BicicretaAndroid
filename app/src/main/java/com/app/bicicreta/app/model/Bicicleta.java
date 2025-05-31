package com.app.bicicreta.app.model;

import java.io.Serializable;

public class Bicicleta implements Serializable {
    private int id;
    private String modelo;
    private int aro;
    private int quantidadeMarchas;
    private int quilometrosRodados;
    private int tamanhoQuadro;
    private String observacao;

    public Bicicleta(String modelo, int aro, int quantidadeMarchas, int tamanhoQuadro, String observacao){
        this.quilometrosRodados = 0;
        setModelo(modelo);
        setAro(aro);
        setQuantidadeMarchas(quantidadeMarchas);
        setTamanhoQuadro(tamanhoQuadro);
        setObservacao(observacao);
    }

    public Bicicleta(int id, String modelo, int aro, int quantidadeMarchas, int tamanhoQuadro, int quilometrosRodados, String observacao){
        this(modelo, aro, quantidadeMarchas, tamanhoQuadro, observacao);
        this.id = id;
        setQuilometrosRodados(quilometrosRodados);
    }

    public int getId() {
        return id;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        if(modelo == null || (modelo != null && modelo.isEmpty())){
            this.modelo = "SEM NOME";
            return;
        }
        this.modelo = modelo.toUpperCase();
    }

    public int getAro() {
        return aro;
    }

    public void setAro(int aro) {
        this.aro = aro;
        if(aro <= 0){
            this.aro = 29;
        }
    }

    public int getQuantidadeMarchas() {
        return quantidadeMarchas;
    }

    public void setQuantidadeMarchas(int quantidadeMarchas) {
        this.quantidadeMarchas = quantidadeMarchas;
        if(quantidadeMarchas <= 0){
            this.quantidadeMarchas = 21;
        }
    }

    public int getQuilometrosRodados() {
        return quilometrosRodados;
    }

    public void setQuilometrosRodados(int quilometrosRodados) {
        this.quilometrosRodados = Math.max(quilometrosRodados, 0);
    }

    public int getTamanhoQuadro() {
        return tamanhoQuadro;
    }

    public void setTamanhoQuadro(int tamanhoQuadro) {
        this.tamanhoQuadro = tamanhoQuadro;
        if(tamanhoQuadro <= 0){
            this.tamanhoQuadro = 17;
        }
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}