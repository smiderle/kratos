package br.com.doubletouch.vendasup.presentation.view;

import br.com.doubletouch.vendasup.data.entity.License;

/**
 * Created by LADAIR on 26/05/2015.
 */
public interface ExpiredView extends LoadDataView {

    void onSuccessSynchronization();

    void onErrorSynchronization(String message);

    /**
     * Licensa renovada.
     */
    void onRenewedLicense();

    void onExpiredLicense( License license);


}
