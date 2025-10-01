package com.app.bicicreta.app.model;

import com.app.bicicreta.app.utils.DataUtil;
import com.github.mikephil.charting.utils.Utils;

import java.io.Serializable;
import java.time.LocalDate;

public class Troca implements Serializable {
    private int id;
    private int quilometros;
    private int pecaId;
    private LocalDate data;

    public Troca(int quilometros, int pecaId){
        setQuilometros(quilometros);
        setPecaId(pecaId);
        setData(DataUtil.dataAtualString());
    }

    public Troca(int quilometros, int pecaId, String data){
        setQuilometros(quilometros);
        setPecaId(pecaId);
        setData(data);
    }

    public Troca(int id, int quilometros, int pecaId, String data){
        setId(id);
        setQuilometros(quilometros);
        setPecaId(pecaId);
        setData(data);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(int quilometros) {
        this.quilometros = quilometros;
    }

    public int getPecaId() {
        return pecaId;
    }

    public void setPecaId(int pecaId) {
        this.pecaId = pecaId;
    }

    public String getData() {
        return DataUtil.DateToUSString(data);
    }

    public void setData(String data) {
        this.data = DataUtil.USStringToDate(data);
    }
}
