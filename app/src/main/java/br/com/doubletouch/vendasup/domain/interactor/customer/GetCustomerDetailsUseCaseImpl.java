package br.com.doubletouch.vendasup.domain.interactor.customer;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.CustomerService;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class GetCustomerDetailsUseCaseImpl implements GetCustomerDetailsUseCase {

    private Integer customerId;
    private final CustomerService customerService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;


    public GetCustomerDetailsUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerService = new CustomerServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }


    @Override
    public void execute(Integer customerId, Callback callback) {
        if (customerId == null || callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.customerId = customerId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.customerService.getCustomerById(this.customerId, this.repositoryCallback );
    }

    private final CustomerService.CustomerDetailsCallback repositoryCallback =
            new CustomerService.CustomerDetailsCallback() {

                @Override
                public void onCustomerLoaded(Customer customer) {
                    notifyGetCustomerDetailsSuccessfully(customer);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyGetCustomerDetailsSuccessfully(final Customer customer) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onCustomerDataLoaded(customer);
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
