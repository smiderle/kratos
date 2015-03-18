package br.com.doubletouch.vendasup.presentation.presenter;

import android.util.Log;

import java.util.Collection;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableUseCase;
import br.com.doubletouch.vendasup.presentation.view.fragment.CustomerDetailsFinancial;
import  java.util.List;

/**
 * Created by LADAIR on 11/03/2015.
 */
public class CustomerDetailsFinancialPresenter implements Presenter {

    private final GetPriceTableListUseCase getPriceTableListUseCase;
    private final GetPriceTableUseCase getPriceTableUseCase;
    private final GetInstallmentUseCase getInstallmentUseCase;
    private final GetInstallmentListUseCase getInstallmentListUseCase;
    private CustomerDetailsFinancial customerDetailsFinancial;

    private Customer customer;

    public CustomerDetailsFinancialPresenter(CustomerDetailsFinancial customerDetailsFinancial, GetPriceTableListUseCase getPriceTableListUseCase,  GetPriceTableUseCase getPriceTableUseCase,
                                             GetInstallmentUseCase getInstallmentUseCase, GetInstallmentListUseCase getInstallmentListUseCase) {

        this.getInstallmentUseCase = getInstallmentUseCase;
        this.getInstallmentListUseCase = getInstallmentListUseCase;
        this.getPriceTableListUseCase = getPriceTableListUseCase;
        this.customerDetailsFinancial = customerDetailsFinancial;
        this.getPriceTableUseCase = getPriceTableUseCase;
    }

    public void initializeViewMode(Customer customer){
        this.customer = customer;
        this.loadPriceTable();
        this.loadInstallment();

    }

    public void initializeEditionMode(Customer customer){
        this.customer = customer;
        this.loadPriceTableList();
        this.loadInstallmentList();
    }

    private void loadPriceTableList(){
        this.getPriceTableListUseCase.execute(getPriceTableListCallback);
    }

    private void loadInstallmentList(){
        this.getInstallmentListUseCase.execute(getInstallmentListCallback);
    }

    private void loadPriceTable(){
        this.getPriceTableUseCase.execute(customer.getPriceTable(), getPriceTableCallback);
    }

    private void loadInstallment(){
        this.getInstallmentUseCase.execute(customer.getInstallmentId(), getInstallmentUseCaseCallback);
    }

    private void showError(String message){
        customerDetailsFinancial.showError(message);
    }

    private final  GetPriceTableListUseCase.Callback getPriceTableListCallback = new GetPriceTableListUseCase.Callback() {
        @Override
        public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
            priceTableCollection.size();
            customerDetailsFinancial.loadPriceTableList((List<PriceTable>) priceTableCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };


    private final  GetPriceTableUseCase.Callback getPriceTableCallback = new GetPriceTableUseCase.Callback() {

        @Override
        public void onPriceTableLoaded(PriceTable priceTable) {
            customerDetailsFinancial.loadPriceTable(priceTable);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };

    private final GetInstallmentListUseCase.Callback getInstallmentListCallback = new GetInstallmentListUseCase.Callback(){

        @Override
        public void onInstallmentListLoaded(List<Installment> installmentCollection) {
            customerDetailsFinancial.loadInstallmentList(installmentCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };

    private final GetInstallmentUseCase.Callback getInstallmentUseCaseCallback = new GetInstallmentUseCase.Callback(){


        @Override
        public void onInstallmentLoaded(Installment installment) {
            customerDetailsFinancial.loadInstallment(installment);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
