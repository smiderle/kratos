package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.OrderPayment;

/**
 * Created by LADAIR on 21/04/2015.
 */
public interface OrderPaymentService {

    void save(List<OrderPayment> orderPayments);

    List<OrderPayment> findByOrderID( Long orderItemID ) ;

    void updateNewOrder( Long oldOrderID, Long newOrderId);

    void deleteByOrderId(Long orderItemID);
}
