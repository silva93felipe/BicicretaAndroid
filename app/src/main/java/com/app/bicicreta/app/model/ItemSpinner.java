package com.app.bicicreta.app.model;

public class ItemSpinner {
    private int id;
    private String descricao;

    public ItemSpinner(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
