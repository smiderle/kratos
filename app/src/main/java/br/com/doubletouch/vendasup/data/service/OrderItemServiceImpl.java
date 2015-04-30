package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.OrderItemDAO;
import br.com.doubletouch.vendasup.data.entity.OrderItem;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderItemServiceImpl implements  OrderItemService {

    private OrderItemDAO orderItemDAO;

    public OrderItemServiceImpl() {
        this.orderItemDAO = new OrderItemDAO();
    }

    @Override
    public void save(List<OrderItem> orderItems) {

        orderItemDAO.insert(orderItems);

    }
}
