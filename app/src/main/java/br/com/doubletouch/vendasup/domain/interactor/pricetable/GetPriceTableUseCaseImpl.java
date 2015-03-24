package br.com.doubletouch.vendasup.domain.interactor.pricetable;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.service.PriceTableServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.PriceTableService;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class GetPriceTableUseCaseImpl implements GetPriceTableUseCase {

    private final PriceTableService priceTableService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private GetPriceTableUseCase.Callback callback;
    private Integer idPriceTable;

    public GetPriceTableUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if( threadExecutor == null || postExecutionThread == null ){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.priceTableService = new PriceTableServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    @Override
    public void execute(Integer idPriceTable,Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.idPriceTable = idPriceTable;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.priceTableService.getPriceTable(this.idPriceTable, this.priceTableCallback);
    }


    private final PriceTableService.PriceTableCallback priceTableCallback = new PriceTableService.PriceTableCallback() {

        @Override
        public void onPriceTableLoaded(PriceTable priceTable) {
            notifyGetPriceTableSuccessfully(priceTable);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };


    private void notifyGetPriceTableSuccessfully(final PriceTable pricetable){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPriceTableLoaded(pricetable);
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
