package br.com.doubletouch.vendasup.data.database;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class CustomerDatabaseImpl implements CustomerDatabase {


    private final CustomerPersistence customerPersistence;

    public CustomerDatabaseImpl(CustomerPersistence customerPersistence) {
        this.customerPersistence = customerPersistence;
    }

    @Override
    public synchronized void get(Integer customerId, CustomerDetailsCallback callback) {
        Customer customer = customerPersistence.get(customerId);
        callback.onCustomerLoaded(customer);
    }

    @Override
    public synchronized void list(Integer branchId, CustomerListCallback callback) {

        List<Customer> customers = customerPersistence.getAll(branchId);
        callback.onCustomerListLoaded(customers);
    }

    @Override
    public synchronized void listByFilter(String name, String customerId,Integer branchId, CustomerListFilterCallback callback) {
        List<Customer> customers = customerPersistence.getByNameOrCustomerId(name, customerId, branchId);
        callback.onCustomerListFilterLoaded(customers);
    }

    @Override
    public synchronized void save(Customer customer, CustomerSaveCallback callback) {
        customerPersistence.insert(customer);
        callback.onCustomerSave(customer);
    }
}
