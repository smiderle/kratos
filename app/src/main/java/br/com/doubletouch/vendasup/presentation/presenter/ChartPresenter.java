package br.com.doubletouch.vendasup.presentation.presenter;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Map;

import br.com.doubletouch.vendasup.data.service.ChartService;
import br.com.doubletouch.vendasup.data.service.ChartServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalVendasPorPeriodoUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.ChartView;

/**
 * Created by LADAIR on 23/02/2015.
 */
public class ChartPresenter implements Presenter {

    private final GetTotalVendasPorPeriodoUseCase getTotalVendasPorPeriodoUseCase;

    private ChartView chartView;

    private ChartService chartService;

    private Long dtInicio;
    private Long dtFim;

    public ChartPresenter(ChartView chartView, GetTotalVendasPorPeriodoUseCase getTotalVendasPorPeriodoUseCase) {
        if (chartView == null) {
            throw new IllegalArgumentException("Parametros do construtor não podem ser nulos.");
        }

        this.chartView = chartView;
        this.getTotalVendasPorPeriodoUseCase = getTotalVendasPorPeriodoUseCase;
        chartService = new ChartServiceImpl();
    }

    public void initialize(Long dtInicio, Long dtFinal) {
        this.dtInicio = dtInicio;
        this.dtFim = dtFinal;
        this.load();
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void load() {
        this.showViewLoading();
        this.getTotalVendasPorPeriodoUseCase.execute(this.dtInicio, this.dtFim, this.getInstallmentDetailsCallback);
    }


    private void showViewLoading() {
        this.chartView.showLoading();
    }

    private void hideViewLoading() {
        this.chartView.hideLoading();
    }


    private void showInstallmentDetailsInView(Map<String, Double> values) {

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        int i = 0;
        for (String data : values.keySet()) {
            xVals.add(data);

            Double valor = values.get(data);



            yVals1.add(new BarEntry(new Float(valor), i++ ));
        }


        this.chartView.render(xVals, yVals1);
    }




    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.chartView.getContext(),
                errorBundle.getException());
        this.chartView.showError(errorMessage);
    }


    private final GetTotalVendasPorPeriodoUseCase.Callback getInstallmentDetailsCallback = new GetTotalVendasPorPeriodoUseCase.Callback() {



        @Override
        public void onDataLoaded(Map<String, Double> values) {

            ChartPresenter.this.showInstallmentDetailsInView(values);
            ChartPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            ChartPresenter.this.hideViewLoading();
            ChartPresenter.this.showErrorMessage(errorBundle);

        }
    };


}
