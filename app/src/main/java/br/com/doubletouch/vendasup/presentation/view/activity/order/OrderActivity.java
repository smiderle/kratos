package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.os.Bundle;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;

/**
 * Created by LADAIR on 31/03/2015.
 */
public class OrderActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeActivity(savedInstanceState);
    }

    private void initializeActivity( Bundle savedInstanceState ) {
        addFragment(R.id.fl_fragment, OrderFragment.newInstance());
    }
}
