package br.com.doubletouch.vendasup.domain.repository;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Created by LADAIR on 10/03/2015.
 */
public interface PriceTableService {

    interface PriceTableListCallback {
        void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface PriceTableCallback {
        void onPriceTableLoaded(PriceTable priceTable);

        void onError(ErrorBundle errorBundle);
    }

    void getPriceTableList( PriceTableListCallback priceTableListCallback);

    void getPriceTable( Integer idPriceTable, PriceTableCallback priceTableCallback);


    public void saveOrUpdateSynchronous(List<PriceTable> tabelas) ;

}
