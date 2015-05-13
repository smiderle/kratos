package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.OrderPaymentDAO;
import br.com.doubletouch.vendasup.data.database.script.OrderPaymentDB;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderPaymentServiceImpl implements OrderPaymentService {

    private OrderPaymentDAO orderPaymentDAO;

    public OrderPaymentServiceImpl() {

        orderPaymentDAO = new OrderPaymentDAO();
    }

    @Override
    public void save(List<OrderPayment> orderPayments) {

        orderPaymentDAO.insert(orderPayments);

    }

    @Override
    public List<OrderPayment> findByOrderID(Long orderItemID) {

        return orderPaymentDAO.findByOrderID(orderItemID);

    }

    @Override
    public void updateNewOrder(Long oldOrderID, Long newOrderId) {
        orderPaymentDAO.updateNewOrder(oldOrderID, newOrderId);
    }

    @Override
    public void deleteByOrderId(Long orderItemID) {

        orderPaymentDAO.deleteByOrderId( orderItemID );

    }
}
