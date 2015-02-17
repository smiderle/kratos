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
        DecimalFormat df = new DecimalFormat(",##0.00");
        return comCifrao ? "R$ " + df.format(value) : df.format(value);
    }
}
