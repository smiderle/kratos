package br.com.doubletouch.vendasup.presentation.view.fragment.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerListFilterUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.CustomerListPresenter;
import br.com.doubletouch.vendasup.presentation.view.CustomerListView;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.adapter.CustomersAdapter;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 12/02/2015.
 */
public class CustomerListFragment extends BaseFragment implements CustomerListView {



    private CustomerListPresenter customerListPresenter;
    private KratosLayoutManager kratosLayoutManager;
    private CustomerListListener customerListListener;
    private CustomersAdapter customerAdapter;

    @InjectView(R.id.rv_customers)
    RecyclerView rv_customers;

    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;

    public CustomerListFragment() {
        super();
    }

    private Navigator navigator;

    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof CustomerListListener){
            this.customerListListener = (CustomerListListener) activity;
        }

        this.activity = activity;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_customer_list, container, true);
        ButterKnife.inject(this, fragmentView);
        setupUI();

        navigator = new Navigator();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.loadCustomerList();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_search_view, menu);

        //Pega o Componente.
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //Define um texto de ajuda:
        mSearchView.setQueryHint("Pesquisar");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customerListPresenter.onCustomerFilterChange(s);
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            case R.id.add:
                Intent intentToLaunch = CustomerDetailsActivity.getCallingIntent(activity, 0, ViewMode.INCLUSAO);
                startActivityForResult (intentToLaunch, 1);
                navigator.transitionGo(activity);
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

        GetCustomerListUseCase getCustomerListUseCase = new GetCustomerListUseCaseImpl( threadExecutor, postExecutionThread);
        GetCustomerListFilterUseCase getCustomerListFilterUseCase = new GetCustomerListFilterUseCaseImpl( threadExecutor, postExecutionThread);
        this.customerListPresenter = new CustomerListPresenter(this,getCustomerListUseCase, getCustomerListFilterUseCase);
    }

    @Override
    public void renderCustomerList(List<Customer> customersCollection) {
        if(customersCollection != null){
            if(customerAdapter == null){
                this.customerAdapter = new CustomersAdapter(getActivity(), customersCollection);
            } else {
                this.customerAdapter.setCustomersCollection(customersCollection);
            }

            this.customerAdapter.setOnItemClickListener(onItemClickListener);
            this.rv_customers.setAdapter(customerAdapter);
        }
    }

    @Override
    public void viewCustomer(Customer customer) {
        if(this.customerListListener != null) {
            Intent intentToLaunch = CustomerDetailsActivity.getCallingIntent(activity, customer.getID(), ViewMode.VISUALIZACAO);
            startActivityForResult (intentToLaunch, 1);
            navigator.transitionGo(activity);
        }
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
        return this.getActivity().getApplicationContext();
    }

    private void setupUI(){
        this.kratosLayoutManager = new KratosLayoutManager(getActivity());
        this.rv_customers.setLayoutManager(kratosLayoutManager);
    }

    private CustomersAdapter.OnItemClickListener onItemClickListener = new CustomersAdapter.OnItemClickListener() {
        @Override
        public void onCustomerItemClicked(Customer customer) {
            if(CustomerListFragment.this.customerListPresenter != null && customer != null){
                   CustomerListFragment.this.customerListPresenter.onCustomerClicked(customer);
            }
        }
    };

    /**
     * Carrega os produtos
     */
    private void loadCustomerList(){
        this.customerListPresenter.initialize();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.customerListPresenter.initialize();

    }
}
