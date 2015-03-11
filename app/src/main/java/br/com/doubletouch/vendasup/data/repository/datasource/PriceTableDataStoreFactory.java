package br.com.doubletouch.vendasup.data.repository.datasource;

import android.content.Context;

import br.com.doubletouch.vendasup.data.database.PricetableDatabase;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableDataStoreFactory {

    private final PricetableDatabase pricetableDatabase;

    public PriceTableDataStoreFactory(Context context, PricetableDatabase pricetableDatabase) {
        this.pricetableDatabase = pricetableDatabase;
    }


    public PriceTableDataStore create(){
        PriceTableDataStore customerDataStore = new DatabasePriceTableDataStore(pricetableDatabase);
        return customerDataStore;
    }
}
