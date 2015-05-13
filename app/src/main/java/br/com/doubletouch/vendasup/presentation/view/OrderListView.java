package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;

/**
 * Created by LADAIR on 07/05/2015.
 */
public interface OrderListView extends LoadDataView {

    void renderOrderList(List<Order> orders);

    void viewOrder(Order order);
}
