package br.com.doubletouch.vendasup.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by LADAIR on 27/04/2015.
 */
public class DateUtil {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

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



    public static String formatDateTime(Long dataLong) {

        return formatDateTime(new Date(dataLong));

    }

    public static String formatDateTime(Date date) {

        return new SimpleDateFormat(DATETIME_FORMAT).format(date);

    }

    public static String formatDate(Long dataLong) {

        return formatDate(new Date(dataLong));

    }

    /**
     * Retorna a data utc.
     * Não aplica o gmt  local a data.
     * @param dataLong
     * @return
     */
    public static String formatDateUTC(Long dataLong){

        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
        sf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sf.format(new Date(dataLong));


    }

    public static String formatDate(Date date) {

        return new SimpleDateFormat(DATE_FORMAT).format(date);

    }

}
