package br.com.doubletouch.vendasup.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.util.DateUtil;
import br.com.doubletouch.vendasup.util.DoubleUtil;

/**
 * Created by LADAIR on 30/04/2015.
 */
public class SynchronizationAdapter extends BaseAdapter {

    private List<Order> orders;
    LayoutInflater layoutInflater;

    public SynchronizationAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Order order = (Order) getItem(position);
        View view = layoutInflater.inflate(R.layout.row_sync, null);

        ((TextView) view.findViewById( R.id.tv_order_emissao)).setText(DateUtil.formatDateTime( order.getIssuanceTime() ));
        ((TextView) view.findViewById( R.id.tv_order_total)).setText(DoubleUtil.formatToCurrency(order.getNetValue(), true));

        if(order.getCustomer() != null ){

            ((TextView) view.findViewById( R.id.tv_order_number)).setText( String.valueOf( order.getCustomer().getName() ) );

        }



        return view;
    }
}
