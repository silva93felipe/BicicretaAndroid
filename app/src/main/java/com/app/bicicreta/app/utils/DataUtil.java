package com.app.bicicreta.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Date USStringToDate(String date){
        Date data = null;
        if(date != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                data = format.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }
}
