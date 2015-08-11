package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.CustomerDAO;
import br.com.doubletouch.vendasup.data.database.dao.OrderDAO;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
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

        if(order.getID() == null){

            //Integer nextId = getNextId();
            Integer nextId = getLessNegative();
            order.setID((long) nextId);
            order.setIdMobile(order.getID());

        }


        orderDAO.insert(order);

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

        orderPaymentService.deleteByOrderId( order.getID() );
        orderPaymentService.save( ordersPayments );

        orderItemService.deleteByOrderId( order.getID() );
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

    @Override
    public void getAllSyncPending(Integer branchID, OrderListSyncCallback orderListSyncCallback) {

        CustomerDAO customerDAO = new CustomerDAO();

        try {

            List<Order> allSyncPending = orderDAO.getAllSyncPending(branchID);

            for (Order order : allSyncPending){

                Customer customer = customerDAO.get(order.getCustomer().getID());
                order.setCustomer(customer);
            }

            orderListSyncCallback.onOrderListLoaded(allSyncPending);

        } catch (Exception e) {
            orderListSyncCallback.onError(new RepositoryErrorBundle(e));
        }

    }

    @Override
    public List<Order> getAllSyncPending(Integer branchID) {

            OrderItemService orderItemService = new OrderItemServiceImpl();
            OrderPaymentService orderPaymentService = new OrderPaymentServiceImpl();

            List<Order> orders = orderDAO.getAllSyncPending(branchID);

            for (Order order : orders){

                List<OrderItem> ordersItens = orderItemService.findByOrderID(order.getID());
                order.setOrdersItens(ordersItens);

                List<OrderPayment> ordersPayment = orderPaymentService.findByOrderID(order.getID());
                order.setOrdersPayments(ordersPayment);


            }


        return  orders;



    }

    @Override
    public void getAll( Integer branchID, OrderListCallback orderListCallback ) {

        CustomerService customerSerice = new CustomerServiceImpl();


        try {

            List<Order> orders = orderDAO.getAll(branchID);

            for(Order order : orders){

                Customer customer = customerSerice.get(order.getCustomer().getID());

                order.setCustomer( customer );

            }


            orderListCallback.onOrderListLoaded( orders );

        } catch (Exception e) {

            orderListCallback.onError(new RepositoryErrorBundle(e));

        }

    }


    @Override
    public void updateSync( List<Order> orders ) {

        OrderItemService orderItemService = new OrderItemServiceImpl();
        OrderPaymentService orderPaymentService = new OrderPaymentServiceImpl();

        for( Order order: orders ) {

            orderDAO.updateByIdMobile( order );
            orderItemService.updateNewOrder(order.getIdMobile(), order.getID());
            orderPaymentService.updateNewOrder( order.getIdMobile(), order.getID() );

        }

    }

    @Override
    public void getOrderDatails(Long orderID, OrderDetailsCallback orderDetailsCallback) {

        Order order = orderDAO.get(orderID);

        try {

            OrderItemService orderItemService = new OrderItemServiceImpl();
            OrderPaymentService orderPaymentService = new OrderPaymentServiceImpl();
            InstallmentService installmentService = new InstallmentServiceImpl();
            CustomerService customerService = new CustomerServiceImpl();
            ProductService productService = new ProductServiceImpl();

            List<OrderItem> ordersItens = orderItemService.findByOrderID( order.getID() );
            order.setOrdersItens(ordersItens);

            List<OrderPayment> ordersPayment = orderPaymentService.findByOrderID(order.getID());
            order.setOrdersPayments(ordersPayment);

            Installment installment = installmentService.get(order.getInstallment().getID());
            order.setInstallment(installment);

            Customer customer = customerService.get(order.getCustomer().getID());
            order.setCustomer(customer);

            orderDetailsCallback.onOrderDetailsLoaded(order);

        } catch (Exception e) {

            orderDetailsCallback.onError( new RepositoryErrorBundle( e ) );

        }

    }

    @Override
    public void updateCustomer(Integer oldCustomerId, Integer newCustomerId) {

        orderDAO.updateCustomer( oldCustomerId, newCustomerId );

    }

    @Override
    public void updateInstallment(Integer oldCustomerId, Integer newCustomerId) {

        orderDAO.updateInstallment(oldCustomerId, newCustomerId);

    }




    @Override
    public Integer getNextId() {

        Integer max = orderDAO.max();

        if(max == null){
            max = 0;
        }

        max += 1;

        return max;

    }

    /**
     *Retorna o menor valor, negativo, dos ids, isso porque os pedidos serão salvos com id negativo, para não correr risco de já existir um id igual.
     * O Código do pedido será salvo com valor negativo, pois pode ser que já exista um outro pedido com o mesmo código, quando for feito o update com o retorno do novo id.
     * @return
     */
    @Override
    public Integer getLessNegative() {

        Integer min = orderDAO.min();

        if(min <= 0){

            min -= 1;

        } else {
            min = -1;
        }

        return min;
    }


}
