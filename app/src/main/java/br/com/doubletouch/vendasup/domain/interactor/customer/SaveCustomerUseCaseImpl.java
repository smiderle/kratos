package br.com.doubletouch.vendasup.domain.interactor.customer;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.CustomerService;


/**
 * Created by LADAIR on 08/03/2015.
 */
public class SaveCustomerUseCaseImpl implements SaveCustomerUseCase {

    private final CustomerService customerService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    private Customer customer;

    public SaveCustomerUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerService = new CustomerServiceImpl();
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
        this.customerService.saveCustomer( this.customer, this.repositoryCallback );
    }

    private final CustomerService.CustomerSaveCallback repositoryCallback = new CustomerService.CustomerSaveCallback() {

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
