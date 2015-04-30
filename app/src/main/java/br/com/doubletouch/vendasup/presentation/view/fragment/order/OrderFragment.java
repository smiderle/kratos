package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.order.SaveOrderUseCase;
import br.com.doubletouch.vendasup.domain.interactor.order.SaveOrderUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.OrderPresenter;
import br.com.doubletouch.vendasup.presentation.view.OrderView;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerListActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderPaymentActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderProductActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.customer.CustomerListFragment;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.OrderPaymentUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 01/04/2015.
 */
public class OrderFragment extends BaseFragment implements OrderView {

    public static Order order;

    public static PriceTable lastPriceTableSelected;

    private Navigator navigator;

    private Activity activity;

    private static final int RESULT_CUSTOMER = 1;
    private static final int RESULT_PRODUCTS = 2;
    private static final int RESULT_PAYMENT = 3;

    @InjectView(R.id.rl_order_customer)
    RelativeLayout rl_order_customer;

    @InjectView(R.id.rl_order_cart)
    RelativeLayout rl_order_cart;

    @InjectView(R.id.rl_order_payment)
    RelativeLayout rl_order_payment;

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

    @InjectView(R.id.iv_pyment)
    ImageView iv_pyment;

    @InjectView(R.id.iv_row_customer)
    ImageView iv_row_customer;

    @InjectView(R.id.iv_row_cart)
    ImageView iv_row_cart;

    @InjectView(R.id.iv_row_payment)
    ImageView iv_row_payment;

    @InjectView(R.id.tv_payment)
    TextView tv_payment;

    @InjectView(R.id.btn_new_order)
    Button btn_new_order;

    @InjectView(R.id.btn_menu)
    Button btn_menu;

    @InjectView(R.id.btn_order_save)
    Button btn_order_save;


    @InjectView(R.id.tv_order_number_title)
    TextView tv_order_number_title;

    @InjectView(R.id.tv_order_number)
    TextView tv_order_number;


    @InjectView(R.id.btn_notification)
    Button btn_notification;


    private OrderPresenter orderPresenter;

    public static OrderFragment newInstance() {
        OrderFragment orderFragment = new OrderFragment();
        Bundle argumentsBundle = new Bundle();
        orderFragment.setArguments(argumentsBundle);
        return orderFragment;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createOrder();

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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

    }

    private void bind( View fragmentView ){

        ButterKnife.inject(this, fragmentView);

        rl_order_customer.setOnClickListener(new CustomerOnClickListner());

        rl_order_cart.setOnClickListener(new ProductOnClickListner());

        rl_order_payment.setOnClickListener(new PaymentListner());

    }

    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        SaveOrderUseCase saveOrderUseCase = new SaveOrderUseCaseImpl( threadExecutor, postExecutionThread );

        orderPresenter = new OrderPresenter( this, saveOrderUseCase);

    }

    @Override
    public void orderSaved(Order order) {

        btn_order_save.setVisibility(View.INVISIBLE);
        btn_menu.setVisibility(View.VISIBLE);
        btn_new_order.setVisibility(View.VISIBLE);

        tv_order_number_title.setVisibility(View.VISIBLE);
        tv_order_number.setVisibility(View.VISIBLE);

        tv_order_number.setText( String.valueOf( order.getID() ) );

        mostrarNotificacaoSucess("Pedido salvo com sucesso");

        removeAllListner();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

        mostrarNotificacaoWarning(message);

    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
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

    private class PaymentListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent it = OrderPaymentActivity.getCallingIntent(activity);
            startActivityForResult(it, RESULT_PAYMENT);
            navigator.transitionGo(activity);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_CUSTOMER) {

            if(data != null){

                Customer customer = (Customer) data.getSerializableExtra(CustomerListFragment.FROM_ACTIVITY);

                if(customer != null){
                    setCustomer(customer);
                }

            }


        } else if(requestCode == RESULT_PRODUCTS){

            setOrderItens();

        } else if(requestCode == RESULT_PAYMENT ){

            setPayment();

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
            order.setNetValue(total);

            quantidade += orderItem.getQuantity();

        }

        tv_cart.setText( quantidade+ " produtos no selecionados" );
        tv_order_total.setText(DoubleUtil.formatToCurrency(total, true));

        calculaParcelas();



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

    private void setPayment() {

        List<OrderPayment> ordersPayments = this.order.getOrdersPayments();

        if( ordersPayments != null ){
            iv_pyment.setImageResource(R.drawable.ic_done_white_36dp);
            iv_pyment.setColorFilter(getResources().getColor(R.color.green_primary_color));
            tv_payment.setText( this.order.getInstallment().getDescription() );

        }
    }


    private void calculaParcelas() {

        if( order.getInstallment() != null && order.getOrdersItens() != null && order.getOrdersItens().size() > 0 ){

            OrderPaymentUtil orderPaymentUtil = new OrderPaymentUtil();
            List<OrderPayment> payments = orderPaymentUtil.generate(order.getInstallment(), OrderFragment.order.getNetValue());

            OrderFragment.order.setOrdersPayments(payments);

        }
    }

    private void createOrder(){

        order = new Order();
        order.setBranchID( VendasUp.getBranchOffice().getBranchOfficeID() );
        order.setOrganizationID( VendasUp.getBranchOffice().getOrganization().getOrganizationID() );
        order.setExcluded( false );
        order.setIssuanceTime( new Date() );
        order.setUser( VendasUp.getUser() );
        order.setUserID( VendasUp.getUser().getUserID() );

        lastPriceTableSelected = null;
    }


    @OnClick(R.id.btn_order_save)
    public void saveOrder(){

        orderPresenter.saveOrder(order);

    }

    @OnClick(R.id.btn_menu)
    public void goToMenu(){

        navigator.previousActivity(activity);

    }

    @OnClick(R.id.btn_new_order)
    public void goToNewOrder(){

        Intent intent = activity.getIntent();
        activity.finish();
        startActivity(intent);
        navigator.transitionGo(activity);


    }

    private void mostrarNotificacaoSucess(final String msg){

        btn_notification.setBackgroundResource(R.drawable.notification_success);
        mostrarNotificacao(msg);

    }


    private void mostrarNotificacaoWarning(final String msg){

        btn_notification.setBackgroundResource(R.drawable.notification_warning);
        mostrarNotificacao(msg);

    }

    private void mostrarNotificacao(final String msg){

        btn_notification.setText(msg);
        //É alterado para invisivel e depois para visivel, pois por algum motivo em algumas telas, a animação não esta iniciando. Então quando é alterado de invisivel para visivel a animação inicia.
        btn_notification.setVisibility(View.INVISIBLE);
        btn_notification.setVisibility(View.VISIBLE);
        Animation animBounce = AnimationUtils.loadAnimation(activity, R.anim.move_in_move_out);
        btn_notification.startAnimation( animBounce );


    }

    private void removeAllListner(){

        rl_order_customer.setOnClickListener(null);
        rl_order_payment.setOnClickListener(null);
        rl_order_cart.setOnClickListener(null);

        iv_row_cart.setVisibility(View.INVISIBLE);
        iv_row_customer.setVisibility(View.INVISIBLE);
        iv_row_payment.setVisibility(View.INVISIBLE);
    }



}
