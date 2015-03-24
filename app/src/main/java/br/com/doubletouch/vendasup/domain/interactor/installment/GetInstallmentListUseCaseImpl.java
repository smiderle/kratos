package br.com.doubletouch.vendasup.domain.interactor.installment;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.InstallmentService;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class GetInstallmentListUseCaseImpl implements GetInstallmentListUseCase {

    private final InstallmentService installmentService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetInstallmentListUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if( threadExecutor == null || postExecutionThread == null ){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.installmentService = new InstallmentServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.installmentService.getInstallmentList(this.installmentCallback);
    }


    private final InstallmentService.InstallmentListCallback installmentCallback = new InstallmentService.InstallmentListCallback() {


        @Override
        public void onInstallmentListLoaded(List<Installment> installmentCollection) {
            notifyGetInstallmentListSuccessfully(installmentCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };


    private void notifyGetInstallmentListSuccessfully(final List<Installment> installments){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onInstallmentListLoaded(installments);
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
