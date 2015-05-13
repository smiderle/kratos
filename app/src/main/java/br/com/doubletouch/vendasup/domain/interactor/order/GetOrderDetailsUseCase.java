package br.com.doubletouch.vendasup.domain.interactor.order;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 11/05/2015.
 */
public interface GetOrderDetailsUseCase extends Interactor {


    interface Callback {
        void onOrderDetailsLoaded( Order order);
        void onError(ErrorBundle errorBundle);
    }


    /**
     *
     * @param callback
     */
    void execute(Long orderId, Callback callback);
}
