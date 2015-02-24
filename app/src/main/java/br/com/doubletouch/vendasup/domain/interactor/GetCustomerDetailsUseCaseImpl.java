package br.com.doubletouch.vendasup.domain.interactor;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class GetCustomerDetailsUseCaseImpl implements GetCustomerDetailsUseCase {

    private Integer customerId;
    private final CustomerRepository customerRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;


    public GetCustomerDetailsUseCaseImpl(CustomerRepository customerRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(customerRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerRepository = customerRepository;
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
        this.customerRepository.getCustomerById(this.customerId, this.repositoryCallback );
    }

    private final CustomerRepository.CustomerDetailsCallback repositoryCallback =
            new CustomerRepository.CustomerDetailsCallback() {

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
