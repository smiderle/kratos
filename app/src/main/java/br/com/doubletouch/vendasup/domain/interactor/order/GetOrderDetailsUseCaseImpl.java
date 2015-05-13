package br.com.doubletouch.vendasup.domain.interactor.order;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 11/05/2015.
 */
public class GetOrderDetailsUseCaseImpl implements GetOrderDetailsUseCase {


    private Callback callback;

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;


    private OrderService orderService;

    private Long orderId;

    public GetOrderDetailsUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        this.orderService = new OrderServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }




    @Override
    public void execute(Long orderId, Callback callback) {

        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.orderId = orderId;
        this.callback = callback;
        this.threadExecutor.execute(this);

    }



    @Override
    public void run() {

        this.orderService.getOrderDatails(orderId, orderDetailsCallback);
    }


    private OrderService.OrderDetailsCallback orderDetailsCallback = new OrderService.OrderDetailsCallback() {

        @Override
        public void onOrderDetailsLoaded(Order order) {
            notifyOrderSuccessfully(order);


        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyOrderSuccessfully(final Order order) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onOrderDetailsLoaded( order );
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
