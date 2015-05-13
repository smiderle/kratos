package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.order.GetOrderListUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.OrderListView;

/**
 * Created by LADAIR on 07/05/2015.
 */
public class OrderListPresenter implements  Presenter {

    private final OrderListView orderListView;

    private final GetOrderListUseCase getOrderListUseCase;

    public OrderListPresenter( OrderListView orderListView, GetOrderListUseCase getOrderListUseCase ) {
        this.orderListView = orderListView;
        this.getOrderListUseCase = getOrderListUseCase;
    }


    public void initialize() {
        this.loadOrderList();
    }


    public void onOrderClicked(Order order){
        this.orderListView.viewOrder(order);
    }


    private void loadOrderList() {
        this.showViewLoading();
        this.getOrderList();
    }


    private void getOrderList(){
        this.getOrderListUseCase.execute( getOrderListCallback );
    }


    private void showOrdersCollectionInView(List<Order> orders){
        this.orderListView.renderOrderList((List<Order>) orders);
    }


    private void showViewLoading() {
        this.orderListView.showLoading();
    }

    private void hideViewLoading() {
        this.orderListView.hideLoading();
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.orderListView.getContext(),
                errorBundle.getException());
        this.orderListView.showError(errorMessage);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    private GetOrderListUseCase.Callback getOrderListCallback = new GetOrderListUseCase.Callback() {
        @Override
        public void onOrderListLoaded(List<Order> orders) {

            OrderListPresenter.this.showOrdersCollectionInView(orders);
            OrderListPresenter.this.hideViewLoading();

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            OrderListPresenter.this.hideViewLoading();
            showErrorMessage(errorBundle);

        }
    };
}
