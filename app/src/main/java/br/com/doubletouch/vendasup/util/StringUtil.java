package br.com.doubletouch.vendasup.util;

/**
 * Classe utilitaria para Strings
 * Created by LADAIR on 11/02/2015.
 */
public class StringUtil {

    public static String DECIMAL_REGEX = "^\\-?(\\d{0,7}|\\d{0,7}\\.\\d{0,2})$";


    /**
     * Retorna uma String vazia, caso o texto seja nulo.
     * @param text
     * @return
     */
    public static String nullToEmpty(String text){
        return text == null ? "" : text;
    }

    public static boolean isOnlyNumbers(String text){

        boolean only = false;

        if( text != null ){

            text = text.replaceAll("\\s", "");

            if( text.matches( "[0-9]*" ) ){

                only = true;

            }

        }

        return only;

    }

}
