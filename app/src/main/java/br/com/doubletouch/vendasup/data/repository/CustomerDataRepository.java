package br.com.doubletouch.vendasup.data.repository;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.exception.CustomerNotFoundException;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;
import br.com.doubletouch.vendasup.data.repository.datasource.CustomerDataStore;
import br.com.doubletouch.vendasup.data.repository.datasource.CustomerDataStoreFactory;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class CustomerDataRepository implements CustomerRepository {


    private final CustomerDataStoreFactory customerDataStoreFactory;


    private static CustomerDataRepository INSTANCE;

    public static synchronized CustomerDataRepository getInstace(CustomerDataStoreFactory dataStoreFactory) {
        if(INSTANCE == null){
            INSTANCE = new CustomerDataRepository(dataStoreFactory);
        }

        return INSTANCE;
    }

    public CustomerDataRepository(CustomerDataStoreFactory customerDataStoreFactory) {
        if(customerDataStoreFactory == null){
            throw  new IllegalArgumentException("Não é permitido parametros nulos no construtor.");
        }

        this.customerDataStoreFactory = customerDataStoreFactory;
    }

    @Override
    public void getCustomerList(Integer branchId, final CustomerListCallback customerListCallback) {
        final CustomerDataStore customerDataStore = this.customerDataStoreFactory.create();
        customerDataStore.getCustomerList(branchId, new CustomerDataStore.CustomerListCallback() {
            @Override
            public void onCustomerListLoaded(Collection<Customer> customersCollection) {
                customerListCallback.onCustomerListLoaded(customersCollection);
            }

            @Override
            public void onError(Exception exception) {
                customerListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getCustomerListByFilter(String description, String customerId, Integer branchId, final CustomerListFilterCallback customerListFilterCallback) {
        final CustomerDataStore customerDataStore = this.customerDataStoreFactory.create();
        customerDataStore.getCustomerListFilter(description, customerId, branchId, new CustomerDataStore.CustomerListFilterCallback() {
            @Override
            public void onCustomerListFilterLoaded(Collection<Customer> customersCollection) {
                customerListFilterCallback.onCustomerListFilterLoaded(customersCollection);
            }

            @Override
            public void onError(Exception exception) {
                customerListFilterCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getCustomerById(int customerId, final CustomerDetailsCallback customerDetailsCallback) {
        final CustomerDataStore customerDataStore = this.customerDataStoreFactory.create();
        customerDataStore.getCustomerDetails(customerId, new CustomerDataStore.CustomerDetailsCallback() {
            @Override
            public void onCustomerLoaded(Customer customer) {
                if(customer != null){
                    customerDetailsCallback.onCustomerLoaded(customer);
                } else {
                    customerDetailsCallback.onError(new RepositoryErrorBundle(new CustomerNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                customerDetailsCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
