package br.com.doubletouch.vendasup.domain.interactor.target;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 27/04/2015.
 */
public class GetTotalDailySalesUseCaseImpl implements GetTotalDailySalesUseCase {

    private final OrderService orderService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;


    private Callback callback;

    private Long dtInicio;
    private Long dtFim;

    public GetTotalDailySalesUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.orderService = new OrderServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }


    @Override
    public void execute(Long dtInicio, Long dtFim, Callback callback) {

        if (callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.callback = callback;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.threadExecutor.execute(this);

    }

    @Override
    public void run() {

        orderService.getTotalDailySales(
                VendasUp.getUser().getUserID(),
                VendasUp.getBranchOffice().getOrganization().getOrganizationID(),
                VendasUp.getBranchOffice().getBranchOfficeID(), dtInicio, dtFim, totalDailySalesCallback );

    }

    private final OrderService.TotalDailySalesCallback totalDailySalesCallback = new OrderService.TotalDailySalesCallback() {
        @Override
        public void onTotalLoaded(Double total) {

            notifyTotalDailySalesSuccessfully(total);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            notifyError(errorBundle);

        }
    };



    private void notifyTotalDailySalesSuccessfully(final Double total) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onDataLoaded( total );
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
