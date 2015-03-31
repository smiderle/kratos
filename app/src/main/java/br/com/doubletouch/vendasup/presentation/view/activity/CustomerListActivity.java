package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.fragment.customer.CustomerListFragment;


public class CustomerListActivity extends BaseActivity implements CustomerListFragment.CustomerListListener {

    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CustomerListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initialize();
    }


    private void initialize(){
        navigator =  new Navigator();
    }



}
