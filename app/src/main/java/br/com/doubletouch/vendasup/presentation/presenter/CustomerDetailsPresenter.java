package br.com.doubletouch.vendasup.presentation.presenter;

import android.util.Log;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerDetailsUseCase;
import br.com.doubletouch.vendasup.domain.interactor.customer.SaveCustomerUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.CustomerDetailsView;
import br.com.doubletouch.vendasup.presentation.view.fragment.CustomerDetailsFinancial;

/**
 * Created by LADAIR on 23/02/2015.
 */
public class CustomerDetailsPresenter implements Presenter {

    private Integer customerId;

    private final CustomerDetailsView customerDetailsView;
    private final GetCustomerDetailsUseCase getCustomerDetailsUseCase;
    private final SaveCustomerUseCase saveCustomerUseCase;
    private final GetPriceTableListUseCase getPriceTableListUseCase;

    public CustomerDetailsPresenter(CustomerDetailsView customerDetailsView,  GetCustomerDetailsUseCase getCustomerDetailsUseCase, SaveCustomerUseCase saveCustomerUseCase, GetPriceTableListUseCase getPriceTableListUseCase) {
        if (customerDetailsView == null || getCustomerDetailsUseCase == null || saveCustomerUseCase == null || getPriceTableListUseCase == null) {
            throw new IllegalArgumentException("Parametros do construtor não podem ser nulos.");
        }

        this.customerDetailsView = customerDetailsView;
        this.getCustomerDetailsUseCase = getCustomerDetailsUseCase;
        this.saveCustomerUseCase = saveCustomerUseCase;
        this.getPriceTableListUseCase = getPriceTableListUseCase;
    }

    public void initialize(Integer customerId){
        this.customerId = customerId;
        this.loadCustomerDetails();
    }

    public void initializeFinancial(){
        this.getPriceTableListUseCase.execute(getPriceTableCallback);
    }

    public void saveCustomer(Customer customer){
        this.showViewLoading();
        this.saveCustomerExecutor(customer);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void loadCustomerDetails(){
        this.hideViewRetry();
        this.showViewLoading();
        this.getCustomerDetails();
    }


    private void showViewLoading() {
        this.customerDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.customerDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.customerDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.customerDetailsView.hideRetry();
    }


    private void showCustomerDetailsInView(Customer customer) {

        this.customerDetailsView.renderCustomer(customer);
    }

    private void showPriceTableInView(List<PriceTable> tabelas){

    }

    private void customerSaved(){
        this.customerDetailsView.customerSaved();
    }

    private void getCustomerDetails(){
        this.getCustomerDetailsUseCase.execute(this.customerId, this.customerDetailsCallback);
    }

    private void getPriceTableList(){
        this.getPriceTableListUseCase.execute(this.priceTableCallback);
    }


    private void saveCustomerExecutor(Customer customer){
        this.saveCustomerUseCase.execute(customer, this.saveCustomerCallback);
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.customerDetailsView.getContext(),
                errorBundle.getException());
        this.customerDetailsView.showError(errorMessage);
    }

    private final  GetPriceTableListUseCase.Callback getPriceTableCallback = new GetPriceTableListUseCase.Callback() {
        @Override
        public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
            priceTableCollection.size();
            Log.i("", priceTableCollection.size()+"");
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private final GetCustomerDetailsUseCase.Callback customerDetailsCallback = new GetCustomerDetailsUseCase.Callback(){

        @Override
        public void onCustomerDataLoaded(Customer customer) {
            CustomerDetailsPresenter.this.showCustomerDetailsInView(customer);
            CustomerDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            CustomerDetailsPresenter.this.hideViewLoading();
            CustomerDetailsPresenter.this.showErrorMessage(errorBundle);
            CustomerDetailsPresenter.this.showViewRetry();
        }
    };

    private final SaveCustomerUseCase.Callback saveCustomerCallback = new SaveCustomerUseCase.Callback() {
        @Override
        public void onCustomerSaved(Customer customer) {
            CustomerDetailsPresenter.this.customerSaved();
            CustomerDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            CustomerDetailsPresenter.this.hideViewLoading();
            CustomerDetailsPresenter.this.showErrorMessage(errorBundle);
            CustomerDetailsPresenter.this.showViewRetry();
        }
    };


    private final GetPriceTableListUseCase.Callback priceTableCallback = new GetPriceTableListUseCase.Callback(){


        @Override
        public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
            CustomerDetailsPresenter.this.showPriceTableInView((List<PriceTable>) priceTableCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            CustomerDetailsPresenter.this.showErrorMessage(errorBundle);
        }
    };

}
