package br.com.doubletouch.vendasup.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by LADAIR on 18/03/2015.
 */
public class SalesUtil {

    /**
     * Aplica um percentual de desconto ou acréscimo no valor passado por parametro.
     * @param valor
     * @param percentual
     * @param acrescimo
     * @return
     */
    public static Double aplicarPercentual(Double valor, Double percentual, boolean acrescimo) {

        double resultado = 0.00;

        if(valor != null) {

            BigDecimal valorBigDecimal = new BigDecimal(String.valueOf(valor));
            BigDecimal percentualReajuste = new BigDecimal(String.valueOf(percentual));
            BigDecimal cemPorcento = new BigDecimal("100");

            BigDecimal multiply = valorBigDecimal.multiply(percentualReajuste);

            BigDecimal valorReajustar = multiply.divide(cemPorcento);

            if (acrescimo) {
                resultado = valorBigDecimal.add(valorReajustar).setScale(2, RoundingMode.CEILING).doubleValue();
            } else {
                resultado = valorBigDecimal.subtract(valorReajustar).setScale(2, RoundingMode.CEILING).doubleValue();
            }
        }


        return resultado;


    }

    public static Double getPercentual(Double valorTotal, Double valorFinal){

        double resultado = 0.00;

        BigDecimal vtotal = new BigDecimal(String.valueOf(valorTotal));
        BigDecimal vfinal = new BigDecimal(String.valueOf(valorFinal));
        BigDecimal vporcento = new BigDecimal("100");


        BigDecimal multiply = vfinal.multiply(vporcento);
        BigDecimal vresult = multiply.divide(vtotal, RoundingMode.HALF_UP);


        resultado = vporcento.subtract(vresult).setScale(2, RoundingMode.CEILING).doubleValue();

        if( valorTotal > valorFinal ){

            resultado = vporcento.subtract(vresult).setScale(2, RoundingMode.CEILING).doubleValue();

        } else {

            resultado = vresult.subtract(vporcento).setScale(2, RoundingMode.CEILING).doubleValue();


        }

        return resultado;

    }




}
