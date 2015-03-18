package br.com.doubletouch.vendasup.domain.interactor.pricetable;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 12/03/2015.
 */
public interface GetPriceTableUseCase extends Interactor {


    /**
     * Callback usado para notificar quando a collection for carregada ou ocorrer um erro.
     */
    interface Callback {
        void onPriceTableLoaded(PriceTable priceTable);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(Integer idPriceTable, Callback callback);
}
