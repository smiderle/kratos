package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;

/**
 * Created by LADAIR on 07/05/2015.
 */
public class OrderListActivity extends BaseActivity {

    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OrderListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initialize();
    }

    private void initialize(){
        navigator =  new Navigator();
    }

}
