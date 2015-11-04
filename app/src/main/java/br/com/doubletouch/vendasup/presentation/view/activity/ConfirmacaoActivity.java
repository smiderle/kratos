package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.dialog.ConfirmacaoCadastroDialog;
import br.com.doubletouch.vendasup.presentation.view.fragment.ConfirmacaoFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class ConfirmacaoActivity extends  BaseActivity {

    private Navigator navigator;

    private String email;
    private String password;

    private static final String ARGUMENT_EMAIL= "kratos.ARGUMENT_EMAIL";
    private static final String ARGUMENT_PASSWORD= "kratos.ARGUMENT_PASSWORD";

    public static Intent getCallingIntent(Context context, String email, String password) {

        Intent callingIntent = new Intent(context, ConfirmacaoActivity.class);
        callingIntent.putExtra(ARGUMENT_EMAIL, email);
        callingIntent.putExtra(ARGUMENT_PASSWORD, password);

        return callingIntent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initializeActivity(savedInstanceState);
    }


    private void initializeActivity( Bundle savedInstanceState ) {

        if(savedInstanceState == null){
            this.email = getIntent().getStringExtra( ARGUMENT_EMAIL );
            this.password= getIntent().getStringExtra( ARGUMENT_PASSWORD );

            addFragment(R.id.fl_fragment, ConfirmacaoFragment.newInstance(email, password));

        } else {
            this.email = savedInstanceState.getString(ARGUMENT_EMAIL);
            this.password = savedInstanceState.getString(ARGUMENT_PASSWORD);
        }



    }


    private void initialize(){

        navigator =  new Navigator();

    }

}
