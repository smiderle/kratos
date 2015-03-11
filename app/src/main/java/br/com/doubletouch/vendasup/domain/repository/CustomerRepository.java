package br.com.doubletouch.vendasup.domain.repository;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 *
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.Customer}
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface CustomerRepository {


    /**
     * Callback usado para ser notificado quando uma lista de produto for carregada ou um erro ocorrer.
     */
    interface CustomerListCallback {
        void onCustomerListLoaded(Collection<Customer> customersCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface CustomerListFilterCallback {
        void onCustomerListFilterLoaded(Collection<Customer> customersCollection);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Callback usado para ser notificado quando um produto for carregado ou um erro ocorrer.
     */
    interface  CustomerDetailsCallback {
        void onCustomerLoaded(Customer customer);

        void onError(ErrorBundle errorBundle);
    }


    /**
     * Callback usado para notificar quando o {@link Customer} for salvo ou um erro ocorrer.
     */
    interface CustomerSaveCallback {
        void onCustomerSaved(Customer customer);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Obtem um a coleção de {@link br.com.doubletouch.vendasup.data.entity.Customer}.
     * @param customerListCallback  O {@link br.com.doubletouch.vendasup.domain.repository.CustomerRepository.CustomerListCallback} usado para notificar os clients.
     */
    void getCustomerList(Integer branchId, CustomerListCallback customerListCallback);

    /**
     * Busca o cliente pelo filtro informado
     * @param name nome
     * @param customerId
     * @param branchId
     * @param customerListFilterCallback
     */
    void getCustomerListByFilter(String name, String customerId, Integer branchId, CustomerListFilterCallback customerListFilterCallback);

    /**
     *
     * Obtem um {@link br.com.doubletouch.vendasup.data.entity.Customer} pelo id.
     * @param customerId O id do produto que sera carregado
     * @param customerDetailsCallback O {@link br.com.doubletouch.vendasup.domain.repository.CustomerRepository.CustomerDetailsCallback} usado para notificar os clients.
     */
    void getCustomerById(final int customerId, CustomerDetailsCallback customerDetailsCallback);



    /**
     * Salva o usuário.
     * @param customer
     * @param callback
     */
    void saveCustomer(final Customer customer, final CustomerSaveCallback callback);
}
