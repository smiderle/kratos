package br.com.doubletouch.vendasup.data.service;

import java.util.Map;

import br.com.doubletouch.vendasup.data.database.dao.ChartDAO;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class ChartServiceImpl implements ChartService {

    private ChartDAO chartDAO;


    public ChartServiceImpl() {
        this.chartDAO = new ChartDAO();
    }


    @Override
    public void getTotalVendasPorPeriodo(Integer branchID, Long dtInicio, Long dtFim, VendasPorPeriodoCallback totalDailySalesCallback) {

        try {

            Map<String, Double> vendaPorPeriodo = chartDAO.getVendaPorPeriodo(dtInicio, dtFim, branchID);

            totalDailySalesCallback.onTotalCarregado( vendaPorPeriodo );

        } catch (Exception e) {

            totalDailySalesCallback.onError(new RepositoryErrorBundle(e));

        }

    }


}
