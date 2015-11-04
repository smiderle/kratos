package br.com.doubletouch.vendasup.util.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import br.com.doubletouch.vendasup.util.DoubleUtil;

/**
 * Created by ladairsmiderle on 08/10/2015.
 */
public class ChartValueFormatter implements ValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return DoubleUtil.formatToCurrency(new Double(value), true);
    }
}
