package br.com.doubletouch.vendasup.domain.interactor.target;

import java.util.Map;

import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 27/04/2015.
 */
public interface GetTotalVendasPorPeriodoUseCase extends Interactor {


    interface Callback {
        void onDataLoaded(Map<String, Double> values);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * @param callback
     */
    void execute(Long dtInicio, Long dtFim, Callback callback);
}
