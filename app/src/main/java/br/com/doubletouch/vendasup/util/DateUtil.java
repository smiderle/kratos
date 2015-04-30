package br.com.doubletouch.vendasup.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by LADAIR on 27/04/2015.
 */
public class DateUtil {

    /**
     * Retorna a data com a ultimahora, minuto e segundo do dia.
     * @param date
     * @return
     */
    public static Calendar getMaximunDate(Date date){


        Calendar dtFim = new GregorianCalendar();
        dtFim.setTime(date);

        dtFim.set(Calendar.HOUR_OF_DAY, dtFim.getActualMaximum(Calendar.HOUR_OF_DAY) );
        dtFim.set(Calendar.MINUTE, dtFim.getActualMaximum(Calendar.MINUTE) );
        dtFim.set(Calendar.SECOND, dtFim.getActualMaximum(Calendar.SECOND) );

        return  dtFim;

    }

    /**
     * Retorna a data com a primeira hora, minuto e segundo do dia.
     * @param date
     * @return
     */
    public static Calendar getMinimunDate(Date date){

        Calendar dtInicio = new GregorianCalendar();
        dtInicio.setTime(date);

        dtInicio.set(Calendar.HOUR_OF_DAY, dtInicio.getActualMinimum(Calendar.HOUR_OF_DAY) );
        dtInicio.set(Calendar.MINUTE, dtInicio.getActualMinimum(Calendar.MINUTE) );
        dtInicio.set(Calendar.SECOND, dtInicio.getActualMinimum(Calendar.SECOND) );

        return dtInicio;
    }
}
