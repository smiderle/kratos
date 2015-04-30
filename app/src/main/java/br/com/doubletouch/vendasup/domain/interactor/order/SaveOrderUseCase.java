package br.com.doubletouch.vendasup.domain.interactor.order;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 22/04/2015.
 */
public interface SaveOrderUseCase  extends Interactor {

    /**
     * Callback usado para notificar quando a collection for carregada ou ocorrer um erro.
     */
    interface Callback {
        void onOrderSaved( Order order );
        void onError(ErrorBundle errorBundle);
    }


    /**
     *
     * @param callback
     */
    void execute(Order order,Callback callback);

}
