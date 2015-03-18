package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.CustomerDAO;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.exception.CustomerNotFoundException;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;
import br.com.doubletouch.vendasup.domain.repository.CustomerService;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class CustomerServiceImpl implements CustomerService {

    CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = new CustomerDAO();
    }


    public void getCustomerList( final CustomerListCallback customerListCallback) {

        try{

            List<Customer> customers = customerDAO.getAll(VendasUp.getUsuarioLogado().getBranchID());
            customerListCallback.onCustomerListLoaded(customers);

        } catch (Exception e){
            customerListCallback.onError(new RepositoryErrorBundle(e));
        }
    }

    public void getCustomerListByFilter(String description, String customerId, final CustomerListFilterCallback customerListFilterCallback) {

        try{

            List<Customer> customers = customerDAO.getByNameOrCustomerId(description, customerId, VendasUp.getUsuarioLogado().getBranchID());
            customerListFilterCallback.onCustomerListFilterLoaded(customers);

        } catch (Exception e){
            customerListFilterCallback.onError(new RepositoryErrorBundle(e));
        }

    }

    public void getCustomerById(int customerId, final CustomerDetailsCallback customerDetailsCallback) {

        try{

            Customer customer = customerDAO.get(customerId);

            if(customer != null){
                customerDetailsCallback.onCustomerLoaded(customer);
            } else {
                customerDetailsCallback.onError(new RepositoryErrorBundle(new CustomerNotFoundException()));
            }

        }catch (Exception e) {

            customerDetailsCallback.onError(new RepositoryErrorBundle(e));
        }

    }

    @Override
    public void saveCustomer(Customer customer,final CustomerSaveCallback customerSaveCallback) {

        try{
            customerDAO.insert(customer);
            customerSaveCallback.onCustomerSaved(customer);
        }catch (Exception e) {
            customerSaveCallback.onError(new RepositoryErrorBundle(e));

        }
    }

    @Override
    public void saveOrUpdateSynchronous(List<Customer> customers) {
        customerDAO.insert(customers);
    }
}
