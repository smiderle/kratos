package br.com.doubletouch.vendasup.controller;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;


import br.com.doubletouch.vendasup.data.database.sqlite.product.CustomerSQLite;
import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class CustomerController {

    public void saveOrUpdate(List<Customer> customers){

        CustomerSQLite customerSQLite= new CustomerSQLite();
        customerSQLite.insert(customers);

    }


}
