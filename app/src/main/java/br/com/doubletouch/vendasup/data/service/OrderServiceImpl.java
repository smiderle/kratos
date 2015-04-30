package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.OrderDAO;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderServiceImpl implements  OrderService {

    private OrderDAO orderDAO;


    public OrderServiceImpl() {
        this.orderDAO = new OrderDAO();
    }



    @Override
    public void save(Order order, OrderSaveCallback orderSaveCallback) {

        Long orderId = orderDAO.insert(order);
        order.setID(orderId);


        List<OrderPayment> ordersPayments = order.getOrdersPayments();

        for( OrderPayment orderPayment : ordersPayments ) {

            orderPayment.setOrder(order);

        }

        List<OrderItem> ordersItens = order.getOrdersItens();

        for ( OrderItem orderItem : ordersItens ){

            orderItem.setOrder( order );

        }

        OrderPaymentService orderPaymentService = new OrderPaymentServiceImpl();
        OrderItemService orderItemService = new OrderItemServiceImpl();

        orderPaymentService.save( ordersPayments );

        orderItemService.save(ordersItens);

        orderSaveCallback.onOrderSaved( order );

    }

    @Override
    public void getTotalDailySales( Integer userID, Integer organizationID, Integer branchID, Long dtInicio, Long dtFim, TotalDailySalesCallback totalDailySalesCallback ) {



        try {


            Double sumSales  = orderDAO.getSumSales(userID, branchID, organizationID, dtInicio, dtFim);
            totalDailySalesCallback.onTotalLoaded(sumSales);

        } catch (Exception e) {

            totalDailySalesCallback.onError( new RepositoryErrorBundle( e ));

        }

    }
}
