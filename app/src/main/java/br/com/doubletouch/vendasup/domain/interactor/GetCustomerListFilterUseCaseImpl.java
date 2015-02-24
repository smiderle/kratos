package br.com.doubletouch.vendasup.domain.interactor;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class GetCustomerListFilterUseCaseImpl implements GetCustomerListFilterUseCase {

    private String name;
    private String customerId;
    private Integer branchId;

    private final CustomerRepository customerRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetCustomerListFilterUseCaseImpl(CustomerRepository customerRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(customerRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerRepository = customerRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }



    @Override
    public void execute(String name, String customerId, Integer branchId, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback não pode ser null!!!");
        }
        this.name = name;
        this.customerId = customerId;
        this.branchId = branchId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.customerRepository.getCustomerListByFilter(name, customerId, branchId, this.customerListFilterCallback);
    }



    private final CustomerRepository.CustomerListFilterCallback customerListFilterCallback = new CustomerRepository.CustomerListFilterCallback() {
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
