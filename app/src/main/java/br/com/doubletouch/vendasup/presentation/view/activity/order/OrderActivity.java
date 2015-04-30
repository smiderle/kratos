package br.com.doubletouch.vendasup.presentation.view.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 31/03/2015.
 */
public class OrderActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OrderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeActivity(savedInstanceState);

    }

    private void initializeActivity( Bundle savedInstanceState ) {
        addFragment(R.id.fl_fragment, OrderFragment.newInstance());
    }
}
