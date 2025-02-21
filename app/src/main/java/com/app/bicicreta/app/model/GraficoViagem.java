package com.app.bicicreta.app.model;

public class GraficoViagem {
    private int quantidadeViagens;
    private String mes;

    public GraficoViagem(int quantidadeViagens, String mes) {
        this.quantidadeViagens = quantidadeViagens;
        this.mes = converterMes(mes);
    }

    private String converterMes(String mesNumero){
        switch (mesNumero){
            case "01":
                return "JAN";
            case "02":
                return "FEV";
            case "03":
                return "MAR";
            case "04":
                return "ABR";
            case "05":
                return "MAI";
            case "06":
                return "JUN";
            case "07":
                return "JUL";
            case "08":
                return "AGO";
            case "09":
                return "SET";
            case "10":
                return "OUT";
            case "11":
                return "NOV";
            case "12":
                return "DEZ";
            default:
                return "";
        }
    }

    public int getQuantidadeViagens() {
        return quantidadeViagens;
    }

    public void setQuantidadeViagens(int quantidadeViagens) {
        this.quantidadeViagens = quantidadeViagens;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
