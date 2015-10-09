package br.com.doubletouch.vendasup.presentation.view.activity.chart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class BarChartActivity extends BaseActivity {

    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, BarChartActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bar_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initialize();
    }

    private void initialize() {
        navigator = new Navigator();
    }

}
