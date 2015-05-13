package br.com.doubletouch.vendasup.data.service;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 *
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.Customer}
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface CustomerService {


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
     * @param customerListCallback  O {@link CustomerService.CustomerListCallback} usado para notificar os clients.
     */
    void getCustomerList(CustomerListCallback customerListCallback);

    /**
     * Busca o cliente pelo filtro informado
     * @param name nome
     * @param customerId
     * @param customerListFilterCallback
     */
    void getCustomerListByFilter(String name, String customerId, CustomerListFilterCallback customerListFilterCallback);

    /**
     *
     * Obtem um {@link br.com.doubletouch.vendasup.data.entity.Customer} pelo id.
     * @param customerId O id do produto que sera carregado
     * @param customerDetailsCallback O {@link CustomerService.CustomerDetailsCallback} usado para notificar os clients.
     */
    void getCustomerById(final int customerId, CustomerDetailsCallback customerDetailsCallback);



    /**
     * Salva o usuário.
     * @param customer
     * @param callback
     */
    void saveCustomer(final Customer customer, final CustomerSaveCallback callback);



    public void saveOrUpdateSynchronous(List<Customer> customers) ;

    Integer getNextId();

    void updateByIdMobile(List<Customer> customers);

    List<Customer> getAllSyncPending(Integer branchId);

    Customer get(Integer id);
}
