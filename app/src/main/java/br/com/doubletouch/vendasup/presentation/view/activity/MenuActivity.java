package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.MenuModel;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.fragment.MenuFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.ProductDetailsFragment;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class MenuActivity extends BaseActivity implements MenuFragment.MenuListListener {

    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();
    }




    private void initialize(){
        navigator =  new Navigator();
    }

    @Override
    public void onMenuClicked(MenuModel menuModel) {
        this.navigator.navigateTo(MenuActivity.this, menuModel.getTo());
    }
}
