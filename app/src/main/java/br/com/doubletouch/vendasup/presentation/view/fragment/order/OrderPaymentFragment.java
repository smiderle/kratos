package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;
import br.com.doubletouch.vendasup.data.entity.enumeration.Payment;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.OrderPaymentPresenter;
import br.com.doubletouch.vendasup.presentation.view.adapter.OrderPaymentAdapter;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.OrderPaymentUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 16/04/2015.
 */
public class OrderPaymentFragment extends BaseFragment implements OrderPaymentView {

    private Navigator navigator;

    private Activity activity;

    @InjectView(R.id.sp_customer_installment)
    Spinner sp_customer_installment;

    @InjectView(R.id.sp_customer_payment)
    Spinner sp_customer_payment;

    @InjectView(R.id.tv_order_total)
    TextView tv_order_total;

    @InjectView(R.id.lv_order_installment)
    ListView lv_order_installment;

    private OrderPaymentPresenter orderPaymentPresenter;

    private OrderPaymentAdapter orderPaymentAdapter;

    private List<Installment> installments;

    public static OrderPaymentFragment newInstance() {
        OrderPaymentFragment orderPaymentFragment = new OrderPaymentFragment();
        Bundle argumentsBundle = new Bundle();
        orderPaymentFragment.setArguments(argumentsBundle);
        return orderPaymentFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = new Navigator();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_order_payment, container, false);

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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_search_view, menu);

        menu.findItem(R.id.it_done).setVisible(true);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            case R.id.it_done:
                navigator.previousActivity(activity);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }





    private void bind( View fragmentView ){

        ButterKnife.inject(this, fragmentView);


        initialize();

        sp_customer_payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                OrderFragment.order.setFormPayment( position +1 );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



    }

    public void initialize() {


        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();


        GetInstallmentListUseCase getInstallmentListUseCase = new GetInstallmentListUseCaseImpl(threadExecutor, postExecutionThread );

        orderPaymentPresenter = new OrderPaymentPresenter(getInstallmentListUseCase, this);
        orderPaymentPresenter.initialize();

        if( OrderFragment.order.getFormPayment() != null ){

            sp_customer_payment.setSelection( OrderFragment.order.getFormPayment() -1 );
        }

    }

    @Override
    public void initializePresenter() {

    }

    private void createListView(){

        orderPaymentAdapter = new OrderPaymentAdapter(activity,  OrderFragment.order.getOrdersPayments());
        lv_order_installment.setAdapter(orderPaymentAdapter);

    }


    @Override
    public void loadInstallmentList(final List<Installment> parcelas) {

        this.installments = parcelas;

        ArrayAdapter<Installment> adapter = new ArrayAdapter<Installment>(activity, android.R.layout.simple_spinner_dropdown_item, parcelas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_customer_installment.setAdapter(adapter);

        for (int i = 0 ; i <  parcelas.size(); i++) {
            Installment installment = parcelas.get(i);

            if(OrderFragment.order.getInstallment() != null){

                if(OrderFragment.order.getInstallment().equals(installment)){

                    sp_customer_installment.setSelection(i);

                    break;
                }


            } else if( OrderFragment.order.getCustomer() != null &&
                    OrderFragment.order.getCustomer().getInstallment() != null &&
                    OrderFragment.order.getCustomer().getInstallment().equals(installment) ) {

                sp_customer_installment.setSelection(i);

                break;
            }
        }

        sp_customer_installment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Installment installment = parcelas.get(position);
                OrderFragment.order.setInstallment(installment);

                calculaParcelas(installment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void renderTotal(Double total) {


        tv_order_total.setText( DoubleUtil.formatToCurrency(total, true) );

    }

    @Override
    public void showError(String message) {

    }

    private void calculaParcelas(Installment installment){

        OrderPaymentUtil orderPaymentUtil = new OrderPaymentUtil();
        List<OrderPayment> payments = orderPaymentUtil.generate(installment, OrderFragment.order.getNetValue());

        OrderFragment.order.setOrdersPayments(payments);
        createListView();

    }

}
