package br.com.doubletouch.vendasup.domain.interactor.customer;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.CustomerService;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class GetCustomerListFilterUseCaseImpl implements GetCustomerListFilterUseCase {

    private String name;
    private String customerId;
    private Integer branchId;

    private final CustomerService customerService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetCustomerListFilterUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }
        this.customerService = new CustomerServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }



    @Override
    public void execute(String name, String customerId, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback não pode ser null!!!");
        }
        this.name = name;
        this.customerId = customerId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.customerService.getCustomerListByFilter(name, customerId, this.customerListFilterCallback);
    }



    private final CustomerService.CustomerListFilterCallback customerListFilterCallback = new CustomerService.CustomerListFilterCallback() {
        @Override
        public void onCustomerListFilterLoaded(Collection<Customer> customersCollection) {
            notifyGetCustomerListSuccessfully(customersCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyGetCustomerListSuccessfully(final Collection<Customer> customersCollection){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCustomerListFilterLoaded(customersCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
