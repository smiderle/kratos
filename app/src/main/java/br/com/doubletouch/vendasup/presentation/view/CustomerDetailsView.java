package br.com.doubletouch.vendasup.presentation.view;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 23/02/2015.
 */
public interface CustomerDetailsView extends LoadDataView {

    void renderCustomer(Customer customer);

    void customerSaved();
}
