package br.com.doubletouch.vendasup.domain.interactor.customer;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.CustomerService;

/**
 * Essa classe é a implementação de {@link GetCustomerListUseCase}
 * Created by LADAIR on 12/02/2015.
 */
public class GetCustomerListUseCaseImpl implements GetCustomerListUseCase {

    private final CustomerService customerService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetCustomerListUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerService = new CustomerServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback não pode ser null!!!");
        }

        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.customerService.getCustomerList( this.pustomerListCallback);
    }

    private final CustomerService.CustomerListCallback pustomerListCallback = new CustomerService.CustomerListCallback() {
        @Override
        public void onCustomerListLoaded(Collection<Customer> pustomersCollection) {
            notifyGetCustomerListSuccessfully(pustomersCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyGetCustomerListSuccessfully(final Collection<Customer> pustomersCollection){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCustomerListLoaded(pustomersCollection);
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
