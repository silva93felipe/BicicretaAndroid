package com.app.bicicreta.app.model;

public class User {
    private int id;
    private String nome;
    public User(String nome){
        this.nome = nome;
    }
    public User(int id, String nome){
        this(nome);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
