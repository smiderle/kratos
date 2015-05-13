package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;

/**
 * Created by LADAIR on 29/04/2015.
 */
public class SynchronizationActivity extends BaseActivity {


    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SynchronizationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sync);
        initialize();
    }


    private void initialize(){

        navigator =  new Navigator();

    }


}
