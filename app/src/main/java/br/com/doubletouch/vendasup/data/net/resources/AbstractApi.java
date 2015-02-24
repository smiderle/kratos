package br.com.doubletouch.vendasup.data.net.resources;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.com.doubletouch.vendasup.VendasUp;

/**
 * Created by LADAIR on 17/02/2015.
 */
public abstract class AbstractApi {

    /**
     * Verifica se existe conexão com a internet
     * @return
     */
    protected boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) VendasUp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
