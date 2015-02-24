package br.com.doubletouch.vendasup.data.repository.datasource;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Interface que representa um data store do local onde os dados são recuperados.
 * Created by LADAIR on 18/02/2015.
 */
public interface CustomerDataStore {

    /**
     * Callback usado para os clients serem notificados quando uma lista de produtos for carregado ou ocorrer algum erro
     */
    interface  CustomerListCallback {
        void onCustomerListLoaded(Collection<Customer> customersCollection);

        void onError(Exception exception);
    }

    interface  CustomerListFilterCallback {
        void onCustomerListFilterLoaded(Collection<Customer> customersCollection);

        void onError(Exception exception);
    }

    /**
     * Callback usado para os clients serem notificados quando um produto for carregado ou ocorrer algum erro
     */
    interface  CustomerDetailsCallback {
        void onCustomerLoaded(Customer customer);

        void onError(Exception exception);
    }

    /**
     * Obtem um a coleção de {@link br.com.doubletouch.vendasup.data.entity.Customer}.
     * @param  branchId Id da Filial
     * @param customerListCallback  O {@link br.com.doubletouch.vendasup.domain.repository.CustomerRepository.CustomerListCallback} usado para notificar os clients.
     */
    void getCustomerList(Integer branchId, CustomerListCallback customerListCallback);


    void getCustomerListFilter(String name, String customerId,Integer branchId, CustomerListFilterCallback customerListFilterCallback);

    /**
     *
     * Obtem um {@link br.com.doubletouch.vendasup.data.entity.Customer} pelo id.
     * @param customerId O id do produto que sera carregado
     * @param customerDetailsCallback O {@link br.com.doubletouch.vendasup.domain.repository.CustomerRepository.CustomerDetailsCallback} usado para notificar os clients.
     */
    void getCustomerDetails(final Integer customerId, CustomerDetailsCallback customerDetailsCallback);
}
