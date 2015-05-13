package br.com.doubletouch.vendasup.domain.interactor.order;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 29/04/2015.
 */
public interface GetOrderSyncPendingUseCase extends Interactor {


    interface Callback {

        void onOrderListLoaded( List<Order> orders);

        void onError(ErrorBundle errorBundle);
    }



    /**
     *
     * @param callback
     */
    void execute(Callback callback);

}
