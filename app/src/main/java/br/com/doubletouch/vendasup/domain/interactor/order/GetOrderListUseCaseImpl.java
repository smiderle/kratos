package br.com.doubletouch.vendasup.domain.interactor.order;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 07/05/2015.
 */
public class GetOrderListUseCaseImpl implements GetOrderListUseCase {

    private Callback callback;

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private OrderService orderService;


    public GetOrderListUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        this.orderService = new OrderServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    @Override
    public void execute(Callback callback) {

        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.callback = callback;
        this.threadExecutor.execute(this);

    }

    @Override
    public void run() {

        this.orderService.getAll(VendasUp.getBranchOffice().getBranchOfficeID(), orderListCallback);
    }

    private OrderService.OrderListCallback orderListCallback = new OrderService.OrderListCallback() {
        @Override
        public void onOrderListLoaded(List<Order> orders) {
            notifySaveOrderSuccessfully(orders);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifySaveOrderSuccessfully(final List<Order> orders) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onOrderListLoaded(orders);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
