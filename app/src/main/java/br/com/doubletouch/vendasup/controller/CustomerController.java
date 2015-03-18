package br.com.doubletouch.vendasup.controller;

import java.util.List;


import br.com.doubletouch.vendasup.data.database.dao.CustomerDAO;
import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class CustomerController {

    public void saveOrUpdate(List<Customer> customers){

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.insert(customers);

    }


}
