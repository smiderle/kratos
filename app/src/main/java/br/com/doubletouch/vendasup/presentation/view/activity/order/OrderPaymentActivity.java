package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderPaymentFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderProductFragment;

/**
 * Created by LADAIR on 16/04/2015.
 */
public class OrderPaymentActivity extends BaseActivity {

    private Navigator navigator;


    public static Intent getCallingIntent(Context context){
        Intent callingIntent = new Intent(context, OrderPaymentActivity.class);
        Bundle bundle = new Bundle();
        callingIntent.putExtras(bundle);
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
            addFragment(R.id.fl_fragment, OrderPaymentFragment.newInstance());
        }

        navigator = new Navigator();
    }
}
