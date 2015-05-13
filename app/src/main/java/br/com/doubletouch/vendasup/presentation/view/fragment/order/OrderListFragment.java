package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.order.GetOrderListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.order.GetOrderListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.OrderListPresenter;
import br.com.doubletouch.vendasup.presentation.view.OrderListView;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderActivity;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.adapter.OrderAdapter;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 07/05/2015.
 */
public class OrderListFragment extends BaseFragment implements OrderListView {

    private Navigator navigator;

    private Activity activity;

    private KratosLayoutManager kratosLayoutManager;

    private OrderListPresenter orderListPresenter;

    private OrderAdapter orderAdapter;

    @InjectView(R.id.rv_customers)
    RecyclerView rv_customers;

    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;


    public static OrderListFragment newInstance() {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle argumentsBundle = new Bundle();
        orderListFragment.setArguments(argumentsBundle);
        return orderListFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, true);
        ButterKnife.inject(this, fragmentView);

        setupUI();

        navigator = new Navigator();

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
        this.orderListPresenter.initialize();

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
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetOrderListUseCase getOrderListUseCase = new GetOrderListUseCaseImpl( threadExecutor, postExecutionThread);
        this.orderListPresenter = new OrderListPresenter( this,getOrderListUseCase);

    }


    private void setupUI(){
        this.kratosLayoutManager = new KratosLayoutManager(getActivity());
        this.rv_customers.setLayoutManager(kratosLayoutManager);
    }


    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void renderOrderList(List<Order> orders) {

        if(orders != null && !orders.isEmpty()){

            if(orderAdapter == null){

                this.orderAdapter = new OrderAdapter( getActivity(), orders );

            } else {

                this.orderAdapter.setOrdersCollection( orders );

            }
            this.orderAdapter.setOnItemClickListener(onItemClickListener);
            this.rv_customers.setAdapter( orderAdapter );
        }



    }


    private OrderAdapter.OnItemClickListener onItemClickListener = new OrderAdapter.OnItemClickListener() {
        @Override
        public void onOrderItemClicked(Order order) {
            if(OrderListFragment.this.orderListPresenter != null && order != null){
                OrderListFragment.this.orderListPresenter.onOrderClicked(order);
            }
        }
    };

    @Override
    public void viewOrder(Order order) {
        ViewMode viewMode;

        if(order.isSyncPending()){

            viewMode = ViewMode.EDICAO;

        } else {

            viewMode = ViewMode.VISUALIZACAO;

        }

        Intent intentToLaunch = OrderActivity.getCallingIntent( activity, order.getID(), viewMode );
        startActivityForResult(intentToLaunch,1);
        navigator.transitionGo(activity);
    }
}
