package br.com.doubletouch.vendasup.domain.interactor;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;
import br.com.doubletouch.vendasup.domain.interactor.SaveCustomerUseCase.Callback;


/**
 * Created by LADAIR on 08/03/2015.
 */
public class SaveCustomerUseCaseImpl implements SaveCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    private Customer customer;

    public SaveCustomerUseCaseImpl(CustomerRepository customerRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(customerRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerRepository = customerRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Customer customer, Callback callback) {
        if (customer == null || callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.customer = customer;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.customerRepository.saveCustomer( this.customer, this.repositoryCallback );
    }

    private final CustomerRepository.CustomerSaveCallback repositoryCallback = new CustomerRepository.CustomerSaveCallback() {

            @Override
            public void onCustomerSaved(Customer customer) {
                notifySaveCustomerSuccessfully(customer);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                notifyError(errorBundle);
            }
    };


    private void notifySaveCustomerSuccessfully(final Customer customer) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onCustomerSaved(customer);
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
