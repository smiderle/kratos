package br.com.doubletouch.vendasup.data.database;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public interface PricetableDatabase {

    interface PriceTableListCallback {
        void onPriceTableListLoaded(Collection<PriceTable> priceTablesCollection);



        void onError(Exception exception);
    }


    void list(final Integer branchId , final PriceTableListCallback callback);

}
