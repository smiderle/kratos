package br.com.doubletouch.vendasup.domain.interactor.pricetable;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 10/03/2015.
 */
public interface GetPriceTableListUseCase extends Interactor {


    /**
     * Callback usado para notificar quando a collection for carregada ou ocorrer um erro.
     */
    interface Callback {
        void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(Callback callback);
}
