package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderPaymentFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderProductFragment;

/**
 * Created by LADAIR on 16/04/2015.
 */
public class OrderPaymentActivity extends BaseActivity {

    private Navigator navigator;

    private ViewMode viewMode;

    static final String INTENT_EXTRA_PARAM_ORDER_EDITION_MODE = "kratos.INTENT_PARAM_ORDER_EDITION_MODE";


    public static Intent getCallingIntent(Context context, ViewMode viewMode){
        Intent callingIntent = new Intent(context, OrderPaymentActivity.class);
        Bundle bundle = new Bundle();
        callingIntent.putExtras(bundle);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ORDER_EDITION_MODE, viewMode);
        return callingIntent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.initializeActivity(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState){
        if(savedInstanceState == null){

            viewMode = (ViewMode) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_ORDER_EDITION_MODE);
            addFragment(R.id.fl_fragment, OrderPaymentFragment.newInstance(viewMode));

        }

        navigator = new Navigator();
    }
}
