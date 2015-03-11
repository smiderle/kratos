package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.doubletouch.vendasup.presentation.navigation.Navigator;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.view.fragment.customer.CustomerDetailsFragment;

/**
 * Created by LADAIR on 21/02/2015.
 */
public class CustomerDetailsActivity extends BaseParallaxActivity {

    private static final String INSTANCE_STATE_PARAM_CUSTOMER__ID = "kratos.STATE_PARAM_CUSTOMER_ID";
    private static final String INTENT_EXTRA_PARAM_CUSTOMER_ID = "kratos.INTENT_PARAM_CUSTOMER_ID";
    private static final String INTENT_EXTRA_PARAM_CUSTOMER_EDITION_MODE = "kratos.INTENT_PARAM_CUSTOMER_EDITION_MODE";
    private static final String INSTANCE_STATE_PARAM_CUSTOMER_EDITION_MODE = "kratos.STATE_PARAM_CUSTOMER_EDITION_MODE";

    private int customerId;
    private boolean editionMode;
    private Navigator navigator;

    public static Intent getCallingIntent(Context context, int customerId, boolean editionMode){
        Intent callingIntent = new Intent(context, CustomerDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CUSTOMER_ID, customerId);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CUSTOMER_EDITION_MODE, editionMode);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeActivity(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(outState != null){
            outState.putInt(INSTANCE_STATE_PARAM_CUSTOMER__ID, this.customerId);
            outState.putBoolean(INSTANCE_STATE_PARAM_CUSTOMER_EDITION_MODE, this.editionMode);
        }
        super.onSaveInstanceState(outState);
    }


    private void initializeActivity( Bundle savedInstanceState ){
        if(savedInstanceState == null){
            this.customerId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CUSTOMER_ID, -1);
            this.editionMode = getIntent().getBooleanExtra(INTENT_EXTRA_PARAM_CUSTOMER_EDITION_MODE, false);

            addFragment(R.id.fl_fragment, CustomerDetailsFragment.newInstance(customerId, editionMode));
        } else {
            this.customerId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CUSTOMER__ID);
            this.editionMode = savedInstanceState.getBoolean(INSTANCE_STATE_PARAM_CUSTOMER_EDITION_MODE);
        }

        navigator = new Navigator();
    }
}
