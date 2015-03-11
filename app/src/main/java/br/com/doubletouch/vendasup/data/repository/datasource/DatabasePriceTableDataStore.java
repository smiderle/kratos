package br.com.doubletouch.vendasup.data.repository.datasource;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.database.PricetableDatabase;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class DatabasePriceTableDataStore implements PriceTableDataStore {

    PricetableDatabase pricetableDatabase;

    public DatabasePriceTableDataStore(PricetableDatabase pricetableDatabase) {
        this.pricetableDatabase = pricetableDatabase;
    }

    @Override
    public void getPriceTableList(Integer branchId, final PriceTableListCallback priceTableListCallback) {
        this.pricetableDatabase.list(branchId, new PricetableDatabase.PriceTableListCallback() {


            @Override
            public void onPriceTableListLoaded(Collection<PriceTable> priceTablesCollection) {
                priceTableListCallback.onPriceTableListLoaded(priceTablesCollection);
            }

            @Override
            public void onError(Exception exception) {
                priceTableListCallback.onError(exception);
            }
        });
    }
}
