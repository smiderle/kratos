package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.OrderItem;

/**
 * Created by LADAIR on 21/04/2015.
 */
public interface OrderItemService {

    void save(List<OrderItem> orderItems);
}
