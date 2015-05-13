package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.order.GetOrderSyncPendingUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.SynchronizationView;

/**
 * Created by LADAIR on 30/04/2015.
 */
public class SynchronizationPresenter implements  Presenter {

    private final SynchronizationView synchronizationView;
    private final GetOrderSyncPendingUseCase getOrderSyncPendingUseCase;

    public SynchronizationPresenter(SynchronizationView synchronizationView, GetOrderSyncPendingUseCase getOrderSyncPendingUseCase) {
        this.synchronizationView = synchronizationView;
        this.getOrderSyncPendingUseCase = getOrderSyncPendingUseCase;
    }

    public void initialize(){
        showViewLoading();
        getOrderList();
    }


    private void showViewLoading() {
        this.synchronizationView.showLoading();
    }

    private void hideViewLoading() {
        this.synchronizationView.hideLoading();
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.synchronizationView.getContext(),
                errorBundle.getException());
        this.synchronizationView.showError(errorMessage);
    }


    private void showOrdersInView(List<Order> orders){
        this.synchronizationView.renderOrderList(orders);
    }

    private void getOrderList(){
        this.getOrderSyncPendingUseCase.execute(getOrderSyncPendingCallback);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private GetOrderSyncPendingUseCase.Callback getOrderSyncPendingCallback = new GetOrderSyncPendingUseCase.Callback() {
        @Override
        public void onOrderListLoaded(List<Order> orders) {
            SynchronizationPresenter.this.showOrdersInView(orders);
            SynchronizationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SynchronizationPresenter.this.hideViewLoading();
            SynchronizationPresenter.this.showErrorMessage(errorBundle);

        }
    };
}
