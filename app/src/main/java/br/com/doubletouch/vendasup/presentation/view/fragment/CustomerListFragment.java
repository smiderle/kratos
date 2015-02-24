package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.database.CustomerDatabase;
import br.com.doubletouch.vendasup.data.database.CustomerDatabaseImpl;
import br.com.doubletouch.vendasup.data.database.CustomerPersistence;
import br.com.doubletouch.vendasup.data.database.sqlite.product.CustomerSQLite;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.data.repository.CustomerDataRepository;
import br.com.doubletouch.vendasup.data.repository.datasource.CustomerDataStoreFactory;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerListFilterUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerListUseCaseImpl;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.presenter.CustomerListPresenter;
import br.com.doubletouch.vendasup.presentation.view.CustomerListView;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.adapter.CustomersAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 12/02/2015.
 */
public class CustomerListFragment extends BaseFragment implements CustomerListView {

    /**
     * Interface para "ouvir" os eventos da lista de clientes.
     */
    public interface CustomerListListener {
        void onCustomerClicked(final Customer customer);
    }


    private CustomerListPresenter customerListPresenter;
    private KratosLayoutManager kratosLayoutManager;
    private CustomerListListener customerListListener;
    private CustomersAdapter customerAdapter;

    @InjectView(R.id.rv_customers)
    RecyclerView rv_customers;

    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @InjectView(R.id.rl_retry)
    RelativeLayout rl_retry;

    @InjectView(R.id.bt_retry)
    Button bt_retry;

    public CustomerListFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof CustomerListListener){
            this.customerListListener = (CustomerListListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_customer_list, container, true);
        ButterKnife.inject(this, fragmentView);
        setupUI();

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
    protected void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        CustomerPersistence customerPersistence = new CustomerSQLite();

        CustomerDatabase customerDatabase = new CustomerDatabaseImpl(customerPersistence);

        CustomerDataStoreFactory customerDataStoreFactory  = new CustomerDataStoreFactory(this.getContext(), customerDatabase);
        CustomerRepository customerRepository = CustomerDataRepository.getInstace(customerDataStoreFactory);
        GetCustomerListUseCase getCustomerListUseCase = new GetCustomerListUseCaseImpl(customerRepository, threadExecutor, postExecutionThread);
        GetCustomerListFilterUseCase getCustomerListFilterUseCase = new GetCustomerListFilterUseCaseImpl(customerRepository, threadExecutor, postExecutionThread);
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
        if(this.customerListListener != null){
            this.customerListListener.onCustomerClicked(customer);
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
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
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
}
