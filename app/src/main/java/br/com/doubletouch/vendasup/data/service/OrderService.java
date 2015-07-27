package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Created by LADAIR on 21/04/2015.
 */
public interface OrderService {


    interface OrderSaveCallback {
        void onOrderSaved(Order order);

        void onError(ErrorBundle errorBundle);
    }

    interface TotalDailySalesCallback {
        void onTotalLoaded( Double total );

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Lista os pedidos que não foram sincronizados.
     */
    interface OrderListSyncCallback {

        void onOrderListLoaded( List<Order> orders);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Lista todos os pedidos.
     */
    interface OrderListCallback {

        void onOrderListLoaded( List<Order> orders);

        void onError(ErrorBundle errorBundle);
    }

    interface  OrderDetailsCallback {

        void onOrderDetailsLoaded( Order order );

        void onError( ErrorBundle errorBundle );
    }

    void save(Order order, OrderSaveCallback orderSaveCallback);

    void getTotalDailySales(Integer userID, Integer organizationID, Integer branchID, Long dtInicio, Long dtFim, TotalDailySalesCallback totalDailySalesCallback);

    void getAllSyncPending( Integer branchID, OrderListSyncCallback orderListSyncCallback );

    List<Order> getAllSyncPending( Integer branchID );

    void getAll( Integer branchID, OrderListCallback orderListCallback );


    Integer getNextId();

    Integer getLessNegative();

    void updateSync( List<Order> orders );

    void getOrderDatails(Long orderID, OrderDetailsCallback orderDetailsCallback);

    void updateCustomer( Integer oldCustomerId, Integer newCustomerId );

}
