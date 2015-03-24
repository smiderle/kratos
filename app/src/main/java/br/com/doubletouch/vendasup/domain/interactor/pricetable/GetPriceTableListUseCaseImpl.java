package br.com.doubletouch.vendasup.domain.interactor.pricetable;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.service.PriceTableServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.PriceTableService;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class GetPriceTableListUseCaseImpl implements GetPriceTableListUseCase {

    private final PriceTableService priceTableService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetPriceTableListUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if( threadExecutor == null || postExecutionThread == null ){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.priceTableService = new PriceTableServiceImpl();
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
        this.priceTableService.getPriceTableList( this.priceTableListCallback );
    }


    private final PriceTableService.PriceTableListCallback priceTableListCallback = new PriceTableService.PriceTableListCallback() {

        @Override
        public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
            notifyGetPriceTableListSuccessfully(priceTableCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };


    private void notifyGetPriceTableListSuccessfully(final Collection<PriceTable> pricetableCollection){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPriceTableListLoaded(pricetableCollection);
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
