package br.com.doubletouch.vendasup.data.repository.datasource;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public interface PriceTableDataStore {

    /**
     * Callback usado para os clients serem notificados quando uma lista de produtos for carregado ou ocorrer algum erro
     */
    interface  PriceTableListCallback {
        void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection);

        void onError(Exception exception);
    }


    void getPriceTableList(Integer branchId, PriceTableListCallback priceTableListCallback);

}
