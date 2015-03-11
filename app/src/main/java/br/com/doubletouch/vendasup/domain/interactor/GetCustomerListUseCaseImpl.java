package br.com.doubletouch.vendasup.domain.interactor;

import java.util.Collection;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;

/**
 * Essa classe é a implementação de {@link br.com.doubletouch.vendasup.domain.interactor.GetCustomerListUseCase}
 * Created by LADAIR on 12/02/2015.
 */
public class GetCustomerListUseCaseImpl implements GetCustomerListUseCase {

    private final CustomerRepository customerRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetCustomerListUseCaseImpl(CustomerRepository customerRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(customerRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerRepository = customerRepository;
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
        this.customerRepository.getCustomerList(VendasUp.getUsuarioLogado().getBranchID(), this.pustomerListCallback);
    }

    private final CustomerRepository.CustomerListCallback pustomerListCallback = new CustomerRepository.CustomerListCallback() {
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
