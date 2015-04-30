package br.com.doubletouch.vendasup.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;

/**
 * Created by LADAIR on 16/04/2015.
 */
public class OrderPaymentUtil {



    public List<OrderPayment> generate(Installment installment, Double total) {

        List<Integer> days = getDays(installment.getInstallmentsDays());
        List<OrderPayment> payments = new ArrayList<>(days.size());


        double valorParcela = total / days.size();

        int i = 0;
        for ( Integer day : days ) {

            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrganizationID(VendasUp.getBranchOffice().getOrganization().getOrganizationID());
            orderPayment.setBranchID(VendasUp.getBranchOffice().getBranchOfficeID());
            orderPayment.setExpirationDate(getExpirationDate(day));
            orderPayment.setInstallmentValue(valorParcela);
            orderPayment.setSequence( i );
            payments.add(orderPayment);

            i++;

        }

        return payments;
    }


    private Date getExpirationDate(Integer day){

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }


    private  List<Integer> getDays(String installmentsDays){

        String[] daysStr = installmentsDays.split(" ");
        List<Integer> days = new ArrayList<>(daysStr.length);

        for( int i = 0; i < daysStr.length; i++ ){

            String day = daysStr[i].trim();
            days.add(Integer.parseInt(day));

        }

        return  days;

    }
}
