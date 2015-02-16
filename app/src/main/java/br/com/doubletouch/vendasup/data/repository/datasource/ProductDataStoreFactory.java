package br.com.doubletouch.vendasup.data.repository.datasource;

import android.content.Context;

import br.com.doubletouch.vendasup.data.database.ProductDatabase;

/**
 * Fabrica para criar diferentes implementações de {@link br.com.doubletouch.vendasup.data.repository.datasource.ProductDataStore}.
 * Created by LADAIR on 12/02/2015.
 */
public class ProductDataStoreFactory {

    private final Context context;

    private final ProductDatabase productDatabase;

    public ProductDataStoreFactory(Context context, ProductDatabase productDatabase) {
        this.context = context;
        this.productDatabase = productDatabase;
    }


    public ProductDataStore create(){
        ProductDataStore productDataStore = new DatabaseProductDataStore(productDatabase);
        return productDataStore;
    }
}
