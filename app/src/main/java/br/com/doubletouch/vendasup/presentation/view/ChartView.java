package br.com.doubletouch.vendasup.presentation.view;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import br.com.doubletouch.vendasup.presentation.view.LoadDataView;

/**
 * Created by ladairsmiderle on 06/10/2015.
 */
public interface ChartView extends LoadDataView {

    void render(ArrayList<String> yVal, ArrayList<BarEntry> xVal);

}