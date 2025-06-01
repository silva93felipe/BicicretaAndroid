package com.app.bicicreta.app.utils;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataUtil {
    public static String DateToUSString(Date date){
        String data = null;
        if(date != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                data = format.format(date);
            }catch (Exception e){}
        }
        return data;
    }

//    public static Date USStringToDate(String date){
//        Date data = null;
//        if(date != null){
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                data = format.parse(date);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return data;
//    }

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
}
