package br.com.doubletouch.vendasup.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerDetailsActivity;
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
            transitionGo( (Activity) context );
        }
    }

    public void navigateToMenu(Context context){
        if(context != null){
            Intent it = MenuActivity.getCallingIntent(context);
            context.startActivity(it);
            transitionGo( (Activity) context );
        }
    }

    /**
     * Navega para os detalhes do produto.
     * @param context
     */
    public void navigateToProductDetails(Context context, Integer productId, ViewMode viewMode){
        if(context != null){
            Intent intentToLaunch = ProductDetailsActivity.getCallingIntent(context, productId,viewMode);
            context.startActivity(intentToLaunch);

            transitionGo( (Activity) context );

        }
    }

    /**
     * Navega para os detalhes do cliente.
     * @param context
     */
    public void navigateToCustomerDetails(Context context, Integer customerId, ViewMode viewMode){
        if(context != null){
            Intent intentToLaunch = CustomerDetailsActivity.getCallingIntent(context, customerId, viewMode);
            context.startActivity(intentToLaunch);
            transitionGo( (Activity) context );
        }
    }


    public void navigateToCustomerDetailsForResult(Activity activity, Integer customerId, ViewMode viewMode){
        Intent intentToLaunch = CustomerDetailsActivity.getCallingIntent(activity, customerId, viewMode);
        activity.startActivityForResult(intentToLaunch, 1);

        transitionGo(activity);
    }


    /**
     * Navega para a activity (.class) passada por parametro
     * @param activity
     * @param to
     */
    public void navigateTo(Activity activity, Class<? extends BaseActivity> to) {
        if(activity != null){
            activity.startActivity(new Intent(activity, to));
            transitionGo( activity );
        }
    }

    /**
     * Finaliza a activity atula, retornando para a anterior.
     * @param activity
     */
    public void previousActivity(Activity activity){
        if(activity != null){
            activity.finish();
            transitionBack(activity);
        }
    }

    private void transitionGo(Activity activity){

        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

    }

    private void transitionBack(Activity activity){

        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
