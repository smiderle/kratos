package br.com.doubletouch.vendasup.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.service.BranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.LicenseService;
import br.com.doubletouch.vendasup.data.service.LicenseServiceImpl;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;

/**
 * Created by LADAIR on 27/05/2015.
 */
public class SplashActivity extends Activity {

    private Navigator navigator = new Navigator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                List< BranchOffice > filiais = new BranchServiceImpl().getBranchList();

                if( filiais == null || filiais.isEmpty() ) {

                    navigator.navigateTo(SplashActivity.this, SignupActivity.class);

                } else {

                    navigator.navigateTo(SplashActivity.this, LoginActivity.class);

                }

                close();

            }
        }, 2000);
    }

    private void close(){
        this.finish();
    }


}


