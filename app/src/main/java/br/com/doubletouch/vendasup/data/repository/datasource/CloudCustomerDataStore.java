package br.com.doubletouch.vendasup.data.repository.datasource;


/**
 * {@link br.com.doubletouch.vendasup.data.repository.datasource.CustomerDataStore implementação baseado nas conexões da api (Cloud)}
 * Created by LADAIR on 18/02/2015
 */
public class CloudCustomerDataStore implements CustomerDataStore {

    @Override
    public void getCustomerList(Integer branchId, CustomerListCallback customerListCallback) {

    }

    @Override
    public void getCustomerListFilter(String description, String customerId, Integer branchId, CustomerListFilterCallback customerListFilterCallback) {

    }


    @Override
    public void getCustomerDetails(Integer customerId, CustomerDetailsCallback customerDetailsCallback) {

    }
}

