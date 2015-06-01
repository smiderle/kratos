package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.Date;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.service.LicenseService;
import br.com.doubletouch.vendasup.data.service.LicenseServiceImpl;
import br.com.doubletouch.vendasup.presentation.view.ExpiredView;

/**
 * Created by LADAIR on 27/05/2015.
 */
public class ExpiredPresenter implements Presenter  {

    ExpiredView expiredView;

    public ExpiredPresenter( ExpiredView expiredView ) {

        this.expiredView = expiredView;

    }

    public void validaDataExpiracao() {


        boolean isValid = true;

        LicenseService licenseService = new LicenseServiceImpl();

        License license = licenseService.findByUserId( VendasUp.getUser().getUserID() );

        if( isLicensaValida( license ) ){

            expiredView.onRenewedLicense();

        } else {

            expiredView.onExpiredLicense(license);

        }

    }

    private boolean isLicensaValida( License license ) {

        boolean isValid = true;

        Date expirateDate = new Date( license.getExpirationDate() );

        if( license.isExpired() ||  expirateDate.before( new Date() ) ){

            isValid = false;

        }

        return isValid;

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
