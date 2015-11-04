package br.com.doubletouch.vendasup.data.service;

import java.util.Map;

import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Created by LADAIR on 21/04/2015.
 */
public interface ChartService {


    /**
     * Lista os pedidos que não foram sincronizados.
     */
    interface VendasPorPeriodoCallback {

        void onTotalCarregado(Map<String, Double> total);

        void onError(ErrorBundle errorBundle);
    }


    void getTotalVendasPorPeriodo(  Integer branchID, Long dtInicio, Long dtFim, VendasPorPeriodoCallback totalDailySalesCallback);

}
