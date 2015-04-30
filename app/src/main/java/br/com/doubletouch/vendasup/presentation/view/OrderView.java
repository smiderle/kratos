package br.com.doubletouch.vendasup.presentation.view;

import br.com.doubletouch.vendasup.data.entity.Order;

/**
 * Created by LADAIR on 21/04/2015.
 */
public interface OrderView extends  LoadDataView {

    void orderSaved(Order order);
}
