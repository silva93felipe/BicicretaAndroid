package com.app.bicicreta.app.model;

public class Bicicleta {
    private int id;
    private String modelo;
    private int aro;
    private int quantidadeMarchas;
    private int quilometrosRodados;
    private int tamanhoQuadro;

    public Bicicleta(String modelo, int aro, int quantidadeMarchas, int tamanhoQuadro){
        setModelo(modelo);
        setAro(aro);
        setQuantidadeMarchas(quantidadeMarchas);
        this.quilometrosRodados = 0;
        setTamanhoQuadro(tamanhoQuadro);
    }

    public Bicicleta(int id, String modelo, int aro, int quantidadeMarchas, int tamanhoQuadro, int quilometrosRodados){
        this(modelo, aro, quantidadeMarchas, tamanhoQuadro);
        this.id = id;
        setQuilometrosRodados(quilometrosRodados);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if(modelo.trim().isEmpty()){
            this.modelo = "SEM NOME";
        }else{
            this.modelo = modelo.toUpperCase();
        }
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
        if(quantidadeMarchas <= 0){
            this.quantidadeMarchas = 21;
        }else{
            this.quantidadeMarchas = quantidadeMarchas;
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
        if(tamanhoQuadro <= 0){
            this.tamanhoQuadro = 17;
        }else{
            this.tamanhoQuadro = tamanhoQuadro;
        }
    }
}