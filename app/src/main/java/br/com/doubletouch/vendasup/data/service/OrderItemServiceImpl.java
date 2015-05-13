package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.OrderItemDAO;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.Product;

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

    @Override
    public List<OrderItem> findByOrderID( Long orderItemID ) {

        ProductService productService = new ProductServiceImpl();

        List<OrderItem> orderItens = orderItemDAO.findByOrderID(orderItemID);

        for( OrderItem orderItem : orderItens ) {

            Integer id = orderItem.getProduct().getID();
            Product product = productService.get(id);
            orderItem.setProduct( product );

        }

        return orderItens;

    }

    @Override
    public void updateNewOrder(Long oldOrderID, Long newOrderId) {

        orderItemDAO.updateNewOrder(oldOrderID, newOrderId);

    }

    @Override
    public void deleteByOrderId(Long orderItemID) {

        orderItemDAO.deleteByOrderId( orderItemID );

    }
}
