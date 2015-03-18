package br.com.doubletouch.vendasup.util;

import java.text.DecimalFormat;

/**
 *
 * Created by LADAIR on 17/02/2015.
 */
public class DoubleUtil {

    /**
     * Formata o valor no formato 1.546,30
     * @return valor formatado
     */
    public static String formatToCurrency(Double value, boolean comCifrao){
        if(value == null)
            value = 0.00;

        DecimalFormat df = new DecimalFormat(",##0.00");
        return comCifrao ? "R$ " + df.format(value) : df.format(value);
    }

    /**
     * Converte uma string em double, caso seja uma string inválida, retorna 0.00.
     * @param value
     * @return
     */
    public static Double toDouble(String value){
        double valor = 0.00;
        try {
            valor = Double.parseDouble(value);
        } catch (NumberFormatException e) {

        }

        return valor;
    }
}
