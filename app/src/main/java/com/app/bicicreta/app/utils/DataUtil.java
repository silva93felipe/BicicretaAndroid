package com.app.bicicreta.app.utils;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataUtil {
    public static String DateToUSString(LocalDate date){
        String data = null;
        if(date != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            data = date.format(formatter);
        }
        return data;
    }

    public static LocalDate USStringToDate(String date){
        LocalDate data = null;
        if(date != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            data = LocalDate.parse(date, format);
        }
        return data;
    }

    public static boolean primeiraDataEhMenorQueASegundaData(String primeira, String segunda){
        if(primeira.equals("") || segunda.equals("")) return false;
        LocalDate primeiraData = USStringToDate(primeira);
        LocalDate segundaData = USStringToDate(segunda);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return primeiraData.isAfter(segundaData);
        }
        return false;
    }

    public static String dataAtualString(){
        String data = "0000-00-00 00:00:00";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            data = dataAtual.format(format);
        }
        return data;
    }
}
