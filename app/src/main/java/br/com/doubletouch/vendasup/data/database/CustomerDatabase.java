package br.com.doubletouch.vendasup.data.database;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Interface que representa o acesso ao banco de dados para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.Customer}
 * Created by LADAIR on 18/02/2015.
 */
public interface CustomerDatabase {


    /**
     * Callback usado para notificar quando uma collection de {@link Customer} for carregado
     */
    interface CustomerListCallback {
        void onCustomerListLoaded(Collection<Customer> customersCollection);

        void onError(Exception exception);
    }

    interface CustomerListFilterCallback {
        void onCustomerListFilterLoaded(Collection<Customer> customersCollection);

        void onError(Exception exception);
    }

    /**
     * Callback usado para notificar quando o {@link Customer} for carregado
     */
    interface CustomerDetailsCallback {
        void onCustomerLoaded(Customer customer);

        void onError(Exception exception);
    }

    /**
     * Recupera um {@link Customer}  do banco de dados
     * @param customerId Id do produto
     * @param callback o {@link br.com.doubletouch.vendasup.data.database.CustomerDatabase.CustomerListCallback} que notificara os clients.
     */
    void get(final Integer customerId, final CustomerDetailsCallback callback);

    /**
     * Recupera uma lista de {@link Customer}  de determinada empresa do banco de dados
     * @param branchId Id da filial
     * @param callback o {@link br.com.doubletouch.vendasup.data.database.CustomerDatabase.CustomerListCallback} que notificara os clients.
     */
    void list(final Integer branchId , final CustomerListCallback callback);

    void listByFilter(final String description, String customerId,Integer branchId, final CustomerListFilterCallback callback);

}
