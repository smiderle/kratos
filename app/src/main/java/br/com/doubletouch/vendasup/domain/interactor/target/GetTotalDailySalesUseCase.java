package br.com.doubletouch.vendasup.domain.interactor.target;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 27/04/2015.
 */
public interface GetTotalDailySalesUseCase extends Interactor {


    /**
     * Callback usado para notificar quando um produto for carregado ou ocorrer um erro.
     */
    interface Callback {
        void onDataLoaded( Double value );
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute( Long dtInicio, Long dtFim, Callback callback );
}
