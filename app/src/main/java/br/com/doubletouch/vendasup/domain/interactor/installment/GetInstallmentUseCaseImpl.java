package br.com.doubletouch.vendasup.domain.interactor.installment;

import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.InstallmentService;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class GetInstallmentUseCaseImpl implements GetInstallmentUseCase {

    private final InstallmentService installmentService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private Integer idInstallment;

    public GetInstallmentUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if( threadExecutor == null || postExecutionThread == null ){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.installmentService = new InstallmentServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    @Override
    public void execute(Integer idInstallment,Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.idInstallment = idInstallment;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.installmentService.getInstallment(this.idInstallment, this.installmentCallback);
    }


    private final InstallmentService.InstallmentCallback installmentCallback = new InstallmentService.InstallmentCallback() {


        @Override
        public void onInstallmentLoaded(Installment installment) {
            notifyGetInstallmentSuccessfully(installment);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };


    private void notifyGetInstallmentSuccessfully(final Installment installment){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onInstallmentLoaded(installment);
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
