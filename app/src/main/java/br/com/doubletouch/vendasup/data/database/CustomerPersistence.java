package br.com.doubletouch.vendasup.data.database;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 17/02/2015.
 */
public interface CustomerPersistence extends Persistence<Customer> {

    public static final String LIMIT = "50";

    public List<Customer> getByNameOrCustomerId(String name, String customerId, Integer branchId);
}
