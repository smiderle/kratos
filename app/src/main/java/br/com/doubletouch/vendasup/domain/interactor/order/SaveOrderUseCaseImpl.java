package br.com.doubletouch.vendasup.domain.interactor.order;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 22/04/2015.
 */
public class SaveOrderUseCaseImpl implements SaveOrderUseCase {


    private Callback callback;

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private OrderService orderService;

    private Order order;

    public SaveOrderUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {


        this.orderService = new OrderServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Order order,Callback callback) {

        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.order = order;
        this.callback = callback;
        this.threadExecutor.execute(this);


    }

    @Override
    public void run() {

        this.orderService.save(order, orderSaveCallback);

    }

    private final OrderService.OrderSaveCallback orderSaveCallback = new OrderService.OrderSaveCallback() {
        @Override
        public void onOrderSaved(Order order) {
            notifySaveOrderSuccessfully(order);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private void notifySaveOrderSuccessfully(final Order order) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onOrderSaved(order);
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
