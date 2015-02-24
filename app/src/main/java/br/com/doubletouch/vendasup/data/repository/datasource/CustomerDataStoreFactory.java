package br.com.doubletouch.vendasup.data.repository.datasource;


import android.content.Context;

import br.com.doubletouch.vendasup.data.database.CustomerDatabase;

/**
 * Fabrica para criar diferentes implementações de {@link br.com.doubletouch.vendasup.data.repository.datasource.CustomerDataStore}.
 * Created by LADAIR on 18/02/2015.
 */
public class CustomerDataStoreFactory {

    private final CustomerDatabase customerDatabase;

    public CustomerDataStoreFactory(Context context, CustomerDatabase customerDatabase) {
        this.customerDatabase = customerDatabase;
    }


    public CustomerDataStore create(){
        CustomerDataStore customerDataStore = new DatabaseCustomerDataStore(customerDatabase);
        return customerDataStore;
    }
}

