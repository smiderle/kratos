package br.com.doubletouch.vendasup.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;
import br.com.doubletouch.vendasup.util.DateUtil;
import br.com.doubletouch.vendasup.util.DoubleUtil;


/**
 * Created by LADAIR on 16/04/2015.
 */
public class OrderPaymentAdapter extends BaseAdapter {

    List<OrderPayment> ordersPayments;
    LayoutInflater minflater;

    public OrderPaymentAdapter(Context context, List<OrderPayment> ordersPayments) {
        minflater = LayoutInflater.from(context);
        this.ordersPayments = ordersPayments;
    }

    @Override
    public int getCount() {
        return ordersPayments.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersPayments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        OrderPayment orderPayment = (OrderPayment) getItem(position);

        view = minflater.inflate(R.layout.row_order_installment, null);


        String seq = (position +1) +"/"+ getCount();
        String vencimento = DateUtil.formatDate(orderPayment.getExpirationDate());

                ((TextView) view.findViewById(R.id.tv_order_numero_parcela)).setText(seq);
        ((TextView) view.findViewById(R.id.tv_order_valor_parcela)).setText(DoubleUtil.formatToCurrency(orderPayment.getInstallmentValue(), false));
        ((TextView) view.findViewById(R.id.tv_order_vencimento)).setText( vencimento );


        return view;
    }
}
