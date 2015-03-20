package br.com.doubletouch.vendasup.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.fragment.ProductListFragment;


public class ProductListActivity extends BaseActivity implements ProductListFragment.ProductListListener {

    private Navigator navigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ProductListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initialize();
    }


    private void initialize(){
        navigator =  new Navigator();
    }


    @Override
    public void onProductClicked(Product product) {
        this.navigator.navigateToProductDetails(this, product.getID(), ViewMode.VISUALIZACAO);
    }

}
