package br.com.doubletouch.vendasup.domain.interactor.installment;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.service.CustomerService;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.data.service.InstallmentService;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.customer.SaveCustomerUseCase;


/**
 * Created by LADAIR on 08/03/2015.
 */
public class SaveInstallmentUseCaseImpl implements SaveInstallmentUseCase {

    private final InstallmentService customerService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    private Installment installment;

    public SaveInstallmentUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.customerService = new InstallmentServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Installment installment, Callback callback) {
        if (installment == null || callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.installment = installment;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.customerService.save( this.installment, this.repositoryCallback );
    }

    private final InstallmentService.InstallmentSaveCallback repositoryCallback = new InstallmentService.InstallmentSaveCallback() {

            @Override
            public void onInstallmentSaved(Installment installment) {
                notifySaveCustomerSuccessfully(installment);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                notifyError(errorBundle);
            }
    };


    private void notifySaveCustomerSuccessfully(final Installment installment) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onInstallmentSaved(installment);
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
