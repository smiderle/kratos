package br.com.doubletouch.vendasup.presentation.presenter;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.order.SaveOrderUseCase;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalDailySalesUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.OrderView;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderPresenter implements Presenter {

    private OrderView orderView;

    private final SaveOrderUseCase saveOrderUseCase;

    public OrderPresenter(OrderView orderView, SaveOrderUseCase saveOrderUseCase) {
        this.orderView = orderView;
        this.saveOrderUseCase = saveOrderUseCase;
    }

    public void saveOrder( Order order ) {

        List<String> errors = validateOrder(order);

        if( errors.isEmpty() ) {

            OrderService orderService = new OrderServiceImpl();

            saveOrderUseCase.execute(order, saveOrderUseCaseCallback);

        } else {

            showErrorMessage( errors.get(0) );

        }

    }

    private SaveOrderUseCase.Callback saveOrderUseCaseCallback = new SaveOrderUseCase.Callback() {
        @Override
        public void onOrderSaved(Order order) {

            orderView.orderSaved(order);


        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };


    /**
     * Valida se o pedido pode ser salvo.
     */
    private List<String> validateOrder( Order order  ) {

        List<String> erros = new ArrayList<>();

        if( order.getCustomer() == null ) {

            erros.add( orderView.getContext().getResources().getString(R.string.msg_order_error_customer) );

        }

        if( order.getOrdersItens() == null || order.getOrdersItens().isEmpty() ) {

            erros.add( orderView.getContext().getResources().getString(R.string.msg_order_error_product) );

        }

        if( order.getOrdersPayments() == null || order.getOrdersPayments().isEmpty() ) {

            erros.add( orderView.getContext().getResources().getString(R.string.msg_order_error_payment) );

        }

        return  erros;

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    private void showErrorMessage( String errorMessage ) {

        this.orderView.showError(errorMessage);
    }
}
