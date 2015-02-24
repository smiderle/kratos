package br.com.doubletouch.vendasup.data.repository.datasource;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.database.CustomerDatabase;
import br.com.doubletouch.vendasup.data.entity.Customer;


/**
 * Created by LADAIR on 23/02/2015.
 */
public class DatabaseCustomersDataStore implements CustomerDataStore {
    // Teste

    CustomerDatabase customerDatabase;

    public DatabaseCustomersDataStore(CustomerDatabase customerDatabase) {
        this.customerDatabase = customerDatabase;
    }

    @Override
    public void getCustomerList(Integer branchId, final CustomerListCallback customerListCallback) {
        this.customerDatabase.list(branchId, new CustomerDatabase.CustomerListCallback() {
            @Override
            public void onCustomerListLoaded(Collection<Customer> customersCollection) {
                customerListCallback.onCustomerListLoaded(customersCollection);
            }

            @Override
            public void onError(Exception exception) {
                customerListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getCustomerListFilter(String name, String customerId,Integer branchId,  final CustomerListFilterCallback customerListFilterCallback) {
        this.customerDatabase.listByFilter(name,customerId,branchId, new CustomerDatabase.CustomerListFilterCallback() {
            @Override
            public void onCustomerListFilterLoaded(Collection<Customer> customersCollection) {
                customerListFilterCallback.onCustomerListFilterLoaded(customersCollection);
            }

            @Override
            public void onError(Exception exception) {
                customerListFilterCallback.onError(exception);
            }
        });
    }

    @Override
    public void getCustomerDetails(Integer customerId, final CustomerDetailsCallback customerDetailsCallback) {
        this.customerDatabase.get(customerId, new CustomerDatabase.CustomerDetailsCallback() {
            @Override
            public void onCustomerLoaded(Customer customer) {
                customerDetailsCallback.onCustomerLoaded(customer);
            }

            @Override
            public void onError(Exception exception) {
                customerDetailsCallback.onError(exception);
            }
        });
    }
}
