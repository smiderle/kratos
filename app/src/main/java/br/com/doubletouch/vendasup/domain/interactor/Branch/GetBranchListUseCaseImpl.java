package br.com.doubletouch.vendasup.domain.interactor.branch;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.service.BranchService;
import br.com.doubletouch.vendasup.data.service.BranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.CustomerService;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerListUseCase;

/**
 * Essa classe é a implementação de {@link br.com.doubletouch.vendasup.domain.interactor.customer.GetCustomerListUseCase}
 * Created by LADAIR on 12/02/2015.
 */
public class GetBranchListUseCaseImpl implements GetBranchListUseCase {

    private final BranchService branchService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetBranchListUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null) {
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.branchService = new BranchServiceImpl();
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
        this.branchService.getBranchList(this.branchListCallback);
    }

    private final BranchService.BranchListCallback branchListCallback = new BranchService.BranchListCallback() {

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }

        @Override
        public void onBranchListLoaded(List<BranchOffice> branches) {
            notifyGetCustomerListSuccessfully( branches );
        }

    };

    private void notifyGetCustomerListSuccessfully(final List<BranchOffice> branches){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onBranchListLoaded(branches);
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
