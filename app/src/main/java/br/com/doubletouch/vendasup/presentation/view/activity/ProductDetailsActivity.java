package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.fragment.ProductDetailsFragment;

/**
 * Activity que mostra os detalhes do produto.
 * Created by LADAIR on 15/02/2015.
 */
public class ProductDetailsActivity extends BaseActivity {

    private Navigator navigator;

    private static final String INTENT_EXTRA_PARAM_PRODUCT_ID = "kratos.INTENT_PARAM_PRODUCT_ID";
    private static final String INSTANCE_STATE_PARAM_PRODUCT_ID = "kratos.STATE_PARAM_PRODUCT_ID";

    private int productId;

    public static Intent getCallingIntent(Context context, int productId){
        Intent callingIntent = new Intent(context, ProductDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_PRODUCT_ID, productId);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initializeActivity(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(outState != null){
            outState.putInt(INSTANCE_STATE_PARAM_PRODUCT_ID, this.productId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState){
        if(savedInstanceState == null){
            this.productId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_PRODUCT_ID, -1);
            addFragment(R.id.fl_fragment, ProductDetailsFragment.newInstance(productId));
        } else {
            this.productId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_PRODUCT_ID);
        }

        navigator = new Navigator();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(ProductDetailsActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }
}
