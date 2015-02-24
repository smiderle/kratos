package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerListUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.CustomerListView;

/**
 * Created by LADAIR on 10/02/2015.
 */
public class CustomerListPresenter implements Presenter {

    private final CustomerListView customerListView;
    private final GetCustomerListUseCase getCustomerListUseCase;
    private final GetCustomerListFilterUseCase getCustomerListFilterUseCase;


    public CustomerListPresenter(CustomerListView customerListView, GetCustomerListUseCase getCustomerListUseCase, GetCustomerListFilterUseCase getCustomerListFilterUseCase ){

        if (customerListView == null || getCustomerListUseCase == null) {
            throw new IllegalArgumentException("Os parametros do Construtor não podem ser nulos!!!");
        }

        this.customerListView = customerListView;
        this.getCustomerListUseCase = getCustomerListUseCase;
        this.getCustomerListFilterUseCase = getCustomerListFilterUseCase;
    }

    public void initialize() {
        this.loadUserList();
    }

    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCustomerList();
    }

    private void showViewLoading() {
        this.customerListView.showLoading();
    }

    private void hideViewLoading() {
        this.customerListView.hideLoading();
    }

    private void hideViewRetry() {
        this.customerListView.hideRetry();
    }

    private void showViewRetry() {
        this.customerListView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.customerListView.getContext(),
                errorBundle.getException());
        this.customerListView.showError(errorMessage);
    }

    private void showCustomersCollectionInView(Collection<Customer> customersCollection){
        this.customerListView.renderCustomerList((List<Customer>) customersCollection);
    }

    private void getCustomerList(){
        this.getCustomerListUseCase.execute(customerListCallback);
    }

    private void getCustomerListFilter(String filter){
        this.getCustomerListFilterUseCase.execute(filter, filter, VendasUp.getUsuarioLogado().getBranchID(), customerListFilterCallback);
    }

    @Override
    public void resume() {
        //empty
    }

    @Override
    public void pause() {
        //empty
    }

    public void onCustomerClicked(Customer customer){
        this.customerListView.viewCustomer(customer);
    }

    public void onCustomerFilterChange(String filter){
        this.getCustomerListFilter(filter);
    }


    private final GetCustomerListUseCase.Callback customerListCallback = new GetCustomerListUseCase.Callback() {
        @Override
        public void onCustomerListLoaded(Collection<Customer> customersCollection) {
            CustomerListPresenter.this.showCustomersCollectionInView(customersCollection);
            CustomerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            CustomerListPresenter.this.hideViewLoading();
            CustomerListPresenter.this.showErrorMessage(errorBundle);
            CustomerListPresenter.this.showViewRetry();


        }
    };

    private final GetCustomerListFilterUseCase.Callback customerListFilterCallback = new GetCustomerListFilterUseCase.Callback() {
        @Override
        public void onCustomerListFilterLoaded(Collection<Customer> customersCollection) {
            CustomerListPresenter.this.showCustomersCollectionInView(customersCollection);
            CustomerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            CustomerListPresenter.this.hideViewLoading();
            CustomerListPresenter.this.showErrorMessage(errorBundle);
            CustomerListPresenter.this.showViewRetry();


        }
    };
}
