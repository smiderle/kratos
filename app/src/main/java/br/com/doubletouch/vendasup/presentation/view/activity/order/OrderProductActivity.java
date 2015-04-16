package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.io.Serializable;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderProductFragment;

/**
 * Created by LADAIR on 06/04/2015.
 */
public class OrderProductActivity extends BaseActivity {

    private Navigator navigator;


    public static Intent getCallingIntent(Context context){
        Intent callingIntent = new Intent(context, OrderProductActivity.class);
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
            addFragment(R.id.fl_fragment, OrderProductFragment.newInstance());
        }

        navigator = new Navigator();
    }


}
