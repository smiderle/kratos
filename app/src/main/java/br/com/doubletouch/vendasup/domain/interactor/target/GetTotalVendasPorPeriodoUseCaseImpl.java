package br.com.doubletouch.vendasup.domain.interactor.target;

import java.util.Map;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.service.ChartService;
import br.com.doubletouch.vendasup.data.service.ChartServiceImpl;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 27/04/2015.
 */
public class GetTotalVendasPorPeriodoUseCaseImpl implements GetTotalVendasPorPeriodoUseCase {

    private final ChartService chartService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;


    private Callback callback;

    private Long dtInicio;
    private Long dtFim;

    public GetTotalVendasPorPeriodoUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if (threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.chartService = new ChartServiceImpl();
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

        chartService.getTotalVendasPorPeriodo(
                VendasUp.getBranchOffice().getBranchOfficeID(),
                dtInicio,
                dtFim, totalDailySalesCallback);

    }

    private final ChartService.VendasPorPeriodoCallback totalDailySalesCallback = new ChartService.VendasPorPeriodoCallback() {


        @Override
        public void onTotalCarregado(Map<String, Double> total) {

            notifyTotalDailySalesSuccessfully(total);

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            notifyError(errorBundle);

        }
    };


    private void notifyTotalDailySalesSuccessfully(final Map<String, Double> total) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onDataLoaded(total);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
