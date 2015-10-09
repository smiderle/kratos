package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalVendasPorPeriodoUseCase;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalVendasPorPeriodoUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.ChartPresenter;
import br.com.doubletouch.vendasup.presentation.view.dialog.DatePickerFragment;
import br.com.doubletouch.vendasup.presentation.view.dialog.DatePickerSelected;
import br.com.doubletouch.vendasup.presentation.view.ChartView;
import br.com.doubletouch.vendasup.util.DateUtil;
import br.com.doubletouch.vendasup.util.chart.ChartValueFormatter;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class BarChartFragment extends BaseFragment implements ChartView {

    private Navigator navigator;

    private Activity activity;

    @InjectView(R.id.chart_barchart)
    public BarChart mChart;

    @InjectView(R.id.btn_calendario)
    public Button btnCalendario;

    private ChartPresenter chartPresenter;

    private SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");

    public static BarChartFragment newInstance() {
        BarChartFragment customerDetailsFragment = new BarChartFragment();
        return customerDetailsFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new Navigator();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView;

        fragmentView = inflater.inflate(R.layout.activity_chart_barchart, container, false);

        ButterKnife.inject(this, fragmentView);


        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerFragment picker = new DatePickerFragment();
                picker.addDatePickerSelected(new DatePickerSelected() {

                    @Override
                    public void dateSelected( Calendar dtSelected ) {


                        Calendar dtInicial = DateUtil.getMinimunDateFromMonth( dtSelected.getTime() );
                        Calendar dtFinal = DateUtil.getMaximunDateFromMonth(dtSelected.getTime());

                        btnCalendario.setText( format.format(dtFinal.getTime()) );


                        chartPresenter.initialize(dtInicial.getTimeInMillis(), dtFinal.getTimeInMillis() );
                    }
                });


                picker.show(getFragmentManager(), "datePicker");


            }
        });


        return fragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Date now = new Date();
        Calendar dtInicial = DateUtil.getMinimunDateFromMonth(now);
        Calendar dtFinal = DateUtil.getMaximunDateFromMonth(now);

        btnCalendario.setText( format.format(dtFinal.getTime()) );

        this.chartPresenter.initialize(dtInicial.getTimeInMillis(), dtFinal.getTimeInMillis());

        setHasOptionsMenu(true);

    }


    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetTotalVendasPorPeriodoUseCase getTotalVendasPorPeriodoUseCase = new GetTotalVendasPorPeriodoUseCaseImpl(threadExecutor, postExecutionThread);
        chartPresenter = new ChartPresenter(this, getTotalVendasPorPeriodoUseCase);

    }

    @Override
    public void showLoading() {

        this.activity.setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {

        this.activity.setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {

        return activity.getApplicationContext();

    }


    @Override
    public void render(ArrayList<String> yVal, ArrayList<BarEntry> xVal) {

        BarDataSet set1 = new BarDataSet(xVal, "R$ Total de Vendas");
        set1.setBarSpacePercent(35f);

        set1.setColor(getResources().getColor(R.color.green_primary_color));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(yVal, dataSets);
        data.setValueTextSize(10f);

        data.setValueFormatter(new ChartValueFormatter());


        mChart.setDescription("");
        mChart.setNoDataText("Nenhuma venda encontrada no mês selecionado");
        mChart.setNoDataTextDescription("Nenhuma venda encontrada no mês selecionado");


        mChart.setData(data);
        mChart.notifyDataSetChanged();
        mChart.invalidate();

    }
}
