package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;

/**
 * Created by LADAIR on 31/03/2015.
 */
public class OrderActivity extends BaseActivity {

    private long orderId;

    private ViewMode viewMode;

    private static final String INTENT_EXTRA_PARAM_ORDER_ID = "kratos.INTENT_PARAM_ORDER_ID";
    private static final String INTENT_EXTRA_PARAM_ORDER_EDITION_MODE = "kratos.INTENT_PARAM_ORDER_EDITION_MODE";
    private static final String INSTANCE_STATE_PARAM_ORDER_ID = "kratos.STATE_PARAM_ORDER_ID";

    public static Intent getCallingIntent(Context context, long orderId, ViewMode viewMode) {

        Intent callingIntent = new Intent(context, OrderActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ORDER_ID, orderId);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ORDER_EDITION_MODE, viewMode);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeActivity(savedInstanceState);

    }

    private void initializeActivity( Bundle savedInstanceState ) {

        if(savedInstanceState == null){
            this.orderId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_ORDER_ID, -1);
            viewMode = (ViewMode) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_ORDER_EDITION_MODE);

            addFragment(R.id.fl_fragment, OrderFragment.newInstance(orderId,viewMode ));
        } else {
            this.orderId = savedInstanceState.getLong(INSTANCE_STATE_PARAM_ORDER_ID);
        }



    }
}
