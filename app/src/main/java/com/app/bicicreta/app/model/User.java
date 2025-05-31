package com.app.bicicreta.app.model;

public class User {
    private int id;
    private String nome;
    public User(String nome){
        setNome(nome);
    }
    public User(int id, String nome){
        this(nome);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || (nome != null && nome.isEmpty())) {
            this.nome = "SEM NOME";
            return;
        }
        this.nome = nome.toUpperCase();
    }
}
