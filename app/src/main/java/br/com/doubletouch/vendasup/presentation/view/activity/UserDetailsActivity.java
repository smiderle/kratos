package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.fragment.user.UserDetailsFragment;

/**
 * Activity que mostra os detalhes do produto.
 * Created by LADAIR on 15/02/2015.
 */
public class UserDetailsActivity extends BaseActivity {

    private Navigator navigator;

    private static final String INTENT_EXTRA_PARAM_USER_EDITION_MODE = "kratos.INTENT_PARAM_USER_EDITION_MODE";
    private static final String INSTANCE_STATE_PARAM_VIEW_MODE = "kratos.STATE_PARAM_PRODUCT_EDITION_MODE";

    private ViewMode viewMode;

    public static Intent getCallingIntent(Context context, ViewMode viewMode) {
        Intent callingIntent = new Intent(context, UserDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_EDITION_MODE, viewMode);
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
        if (outState != null) {
            outState.putSerializable(INSTANCE_STATE_PARAM_VIEW_MODE, viewMode);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.viewMode = (ViewMode) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_USER_EDITION_MODE);

            addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(viewMode));
        } else {
            this.viewMode = (ViewMode) savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_VIEW_MODE);
        }

        navigator = new Navigator();
    }

}
