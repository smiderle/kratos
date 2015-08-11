package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.fragment.InstallmentDetailsFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.customer.CustomerDetailsFragment;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class InstallmentDetailsActivity extends BaseActivity  {


    private static final String INSTANCE_STATE_PARAM_INSTALLMENT_ID = "kratos.STATE_PARAM_INSTALLMENT_ID";
    private static final String INTENT_EXTRA_PARAM_INSTALLMENT_ID = "kratos.INTENT_PARAM_INSTALLMENT_ID";
    private static final String INTENT_EXTRA_PARAM_INSTALLMENT_VIEW_MODE = "kratos.INTENT_PARAM_INSTALLMENT_EDITION_MODE";
    private static final String INSTANCE_STATE_PARAM_CUSTOMER_EDITION_MODE = "kratos.STATE_PARAM_CUSTOMER_EDITION_MODE";

    private Navigator navigator;

    private ViewMode viewMode;

    private int installmentId;

    public static Intent getCallingIntent(Context context,int installmentId, ViewMode viewMode) {
        Intent callingIntent = new Intent(context, InstallmentDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_INSTALLMENT_ID, installmentId);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_INSTALLMENT_VIEW_MODE, viewMode);
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
            outState.putInt(INSTANCE_STATE_PARAM_INSTALLMENT_ID, this.installmentId);
            outState.putSerializable(INSTANCE_STATE_PARAM_CUSTOMER_EDITION_MODE, this.viewMode);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity( Bundle savedInstanceState ){
        if(savedInstanceState == null){
            this.installmentId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_INSTALLMENT_ID, -1);
            viewMode = (ViewMode) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_INSTALLMENT_VIEW_MODE);

            addFragment(R.id.fl_fragment, InstallmentDetailsFragment.newInstance(installmentId, viewMode));
        } else {
            this.installmentId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_INSTALLMENT_ID);
            viewMode = (ViewMode) savedInstanceState.getSerializable(INTENT_EXTRA_PARAM_INSTALLMENT_VIEW_MODE);

        }

        navigator = new Navigator();
    }

}
