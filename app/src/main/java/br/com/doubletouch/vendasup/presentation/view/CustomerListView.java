package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 10/02/2015.
 */
public interface CustomerListView extends  LoadDataView {


    public interface CustomerListListener {

    }

    /**
     * Renderiza a lista de clientes na tela.
     * @param customers
     */
    void renderCustomerList(List<Customer> customers);

    /**
     * Visualiza detalhes do {@link br.com.doubletouch.vendasup.data.entity.Customer}
     * @param customer
     */
    void viewCustomer(Customer customer);
}
