package com.app.bicicreta.app.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class MoedaUtil {
    public static String convertToBR(Double value){
        NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return  df.format(value);
    }

    public static String convertToBR(String value){
        NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return  df.format(value);
    }
}
