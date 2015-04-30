package br.com.doubletouch.vendasup.presentation.presenter;

import android.util.Log;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderPaymentView;

/**
 * Created by LADAIR on 16/04/2015.
 */
public class OrderPaymentPresenter implements Presenter {

    private final GetInstallmentListUseCase getInstallmentListUseCase;
    private OrderPaymentView orderPayment;

    public OrderPaymentPresenter(GetInstallmentListUseCase getInstallmentListUseCase, OrderPaymentView orderPayment) {
        this.getInstallmentListUseCase = getInstallmentListUseCase;
        this.orderPayment = orderPayment;
    }


    public void initialize(){
        this.loadInstallmentList();
        this.loadTotal();
    }


    private void loadInstallmentList(){
        this.getInstallmentListUseCase.execute(getInstallmentListCallback);
    }

    private void loadTotal(){

        orderPayment.renderTotal(OrderFragment.order.getNetValue());

    }


    private final GetInstallmentListUseCase.Callback getInstallmentListCallback = new GetInstallmentListUseCase.Callback(){

        @Override
        public void onInstallmentListLoaded(List<Installment> installmentCollection) {
            orderPayment.loadInstallmentList(installmentCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };

    private void showError(String message){
        orderPayment.showError(message);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
