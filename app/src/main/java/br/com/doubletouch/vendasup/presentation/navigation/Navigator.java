package br.com.doubletouch.vendasup.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.MenuActivity;
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

    public void navigateToMenu(Context context){
        if(context != null){
            Intent it = MenuActivity.getCallingIntent(context);
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

    /**
     * Navega para a activity (.class) passada por parametro
     * @param context
     * @param to
     */
    public void navigateTo(Context context, Class<? extends BaseActivity> to) {
        if(context != null){
            context.startActivity(new Intent(context, to));
        }
    }

    /**
     * Finaliza a activity atula, retornando para a anterior.
     * @param activity
     */
    public void previousActivity(Activity activity){
        if(activity != null){
            activity.finish();
        }
    }
}
