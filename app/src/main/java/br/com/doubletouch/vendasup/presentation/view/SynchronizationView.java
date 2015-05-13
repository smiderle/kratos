package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;

/**
 * Created by LADAIR on 30/04/2015.
 */
public interface SynchronizationView extends LoadDataView {

   void renderOrderList(List<Order> orders);

    void onSuccessSynchronization();

    void onErrorSynchronization(String message);
}
