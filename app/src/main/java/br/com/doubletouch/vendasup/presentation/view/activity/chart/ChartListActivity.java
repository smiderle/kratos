package br.com.doubletouch.vendasup.presentation.view.activity.chart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class ChartListActivity extends BaseActivity {


    private Navigator navigator = new Navigator();


    ListView listView;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ChartListActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(this);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_chart);

        String[] charts = getResources().getStringArray(R.array.charts_type);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, charts));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:

                        navigator.navigateTo(ChartListActivity.this, BarChartActivity.class);
                        break;
                }


            }
        });
    }

}
