package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class SigninActivity extends  BaseActivity {

    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SigninActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initialize();
    }


    private void initialize(){

        navigator =  new Navigator();

    }

}
