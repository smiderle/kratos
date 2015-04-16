package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerListActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderProductActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.customer.CustomerListFragment;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 01/04/2015.
 */
public class OrderFragment extends BaseFragment {

    public static Order order = new Order();

    private Navigator navigator;

    private Activity activity;

    private static final int RESULT_CUSTOMER = 1;
    private static final int RESULT_PRODUCTS = 2;

    @InjectView(R.id.rl_order_customer)
    RelativeLayout rl_order_customer;

    @InjectView(R.id.rl_order_cart)
    RelativeLayout rl_order_cart;

    @InjectView(R.id.tv_order_customer)
    TextView tv_order_customer;

    @InjectView(R.id.tv_cart)
    TextView tv_cart;

    @InjectView(R.id.tv_order_total)
    TextView tv_order_total;

    @InjectView(R.id.iv_cart)
    ImageView iv_cart;

    @InjectView(R.id.iv_customer)
    ImageView iv_customer;

    public static OrderFragment newInstance() {
        OrderFragment orderFragment = new OrderFragment();
        Bundle argumentsBundle = new Bundle();
        orderFragment.setArguments(argumentsBundle);
        return orderFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = new Navigator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_order, container, false);

        bind(fragmentView);

        return fragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    private void bind( View fragmentView ){

        order = new Order();

        ButterKnife.inject(this, fragmentView);

        rl_order_customer.setOnClickListener(new CustomerOnClickListner());

        rl_order_cart.setOnClickListener(new ProductOnClickListner());

    }

    @Override
    public void initializePresenter() {

    }

    private class CustomerOnClickListner implements  View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent it = CustomerListActivity.getCallingIntent(activity);
            it.putExtra(CustomerListFragment.FROM_ACTIVITY , CustomerListFragment.FROM_ORDER_ACTIVITY_CODE);
            startActivityForResult(it, RESULT_CUSTOMER);
            navigator.transitionGo(activity);


        }
    }

    private class ProductOnClickListner implements  View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent it = OrderProductActivity.getCallingIntent(activity);
            startActivityForResult(it, RESULT_PRODUCTS);
            navigator.transitionGo(activity);


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_CUSTOMER) {

            Customer customer = (Customer) data.getSerializableExtra(CustomerListFragment.FROM_ACTIVITY);

            if(customer != null){
                setCustomer(customer);
            }
        } else if(requestCode == RESULT_PRODUCTS){

            setOrderItens();

        }

    }

    private void setOrderItens(){

        List<OrderItem> ordersItens = order.getOrdersItens();

        Double total = 0.0;
        Double quantidade = 0.0;

        if(ordersItens.isEmpty()){
            iv_cart.setImageResource(R.drawable.ic_shopping_cart_grey600_36dp);
            iv_cart.setColorFilter(null);
        } else {
            iv_cart.setImageResource(R.drawable.ic_done_white_36dp);
            iv_cart.setColorFilter(getResources().getColor(R.color.green_primary_color));
        }

        for (OrderItem orderItem : ordersItens){

            if(orderItem.getPrice() == null){
                orderItem.setPrice(orderItem.getProduct().getSalePrice());
            }

            total += ( orderItem.getPrice() * orderItem.getQuantity() );



            quantidade += orderItem.getQuantity();

        }

        tv_cart.setText( quantidade+ " produtos no selecionados" );
        tv_order_total.setText(DoubleUtil.formatToCurrency(total, true));


    }

    private void setCustomer(Customer customer){

        if(customer != null){
            iv_customer.setImageResource(R.drawable.ic_done_white_36dp);
            iv_customer.setColorFilter(getResources().getColor(R.color.green_primary_color));
            tv_order_customer.setText(customer.getName());
        } else {
            iv_customer.setImageResource(R.drawable.ic_person_grey600_36dp);
            iv_customer.setColorFilter(null);
            tv_order_customer.setText(R.string.tv_order_customer);
        }

        this.order.setCustomer(customer);




    }
}
