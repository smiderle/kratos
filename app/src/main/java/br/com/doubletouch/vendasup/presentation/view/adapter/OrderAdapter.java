package br.com.doubletouch.vendasup.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.util.DateUtil;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 07/05/2015.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Order> orders;


    public interface OnItemClickListener {
        void onOrderItemClicked(Order order);
    }

    private OnItemClickListener onItemClickListener;

    public OrderAdapter(Context context, List<Order> orders) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_order, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(view);

        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final Order order = orders.get( position );
        holder.tv_order_customer.setText( order.getCustomer().getName() );
        holder.tv_order_number.setText( String.valueOf( order.getID() ));
        holder.tv_order_total.setText(DoubleUtil.formatToCurrency( order.getNetValue(), false ) );
        holder.tv_order_emissao.setText(DateUtil.formatDateTime( order.getIssuanceTime() ));

        if(order.isSyncPending()){
            holder.iv_order_status.setBackgroundResource(R.drawable.status_pending);
        } else {
            holder.iv_order_status.setBackgroundResource(R.drawable.status_success);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OrderAdapter.this.onItemClickListener != null){
                    OrderAdapter.this.onItemClickListener.onOrderItemClicked(order);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_order_customer)
        TextView tv_order_customer;

        @InjectView(R.id.tv_order_number)
        TextView tv_order_number;

        @InjectView(R.id.tv_order_total)
        TextView tv_order_total;

        @InjectView(R.id.tv_order_emissao)
        TextView tv_order_emissao;

        @InjectView(R.id.iv_order_status)
        View iv_order_status;


        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setOrdersCollection(List<Order> orders){
        this.orders = orders;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
