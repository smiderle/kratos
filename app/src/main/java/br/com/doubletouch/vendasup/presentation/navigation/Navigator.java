package br.com.doubletouch.vendasup.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import br.com.doubletouch.vendasup.presentation.view.activity.ProductDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductListActivity;

/**
 * Created by LADAIR on 10/02/2015.
 */
public class Navigator {

    /**
     * Navega para a lista de produtos
     * @param context
     */
    public void navigateToProductList(Context context){
        if(context != null){
            Intent it = ProductListActivity.getCallingIntent(context);
            context.startActivity(it);
        }
    }

    /**
     * Navega para os detalhes do produto.
     * @param context
     */
    public void navigateToProductDetails(Context context, Integer productId){
        if(context != null){
            Intent intentToLaunch = ProductDetailsActivity.getCallingIntent(context, productId);
            context.startActivity(intentToLaunch);
        }

    }
}
