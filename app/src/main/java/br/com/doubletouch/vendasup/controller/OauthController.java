package br.com.doubletouch.vendasup.controller;

import java.io.IOException;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.net.resources.OauthApi;
import br.com.doubletouch.vendasup.exception.SyncronizationException;
import br.com.doubletouch.vendasup.domain.Credenciais;
import br.com.doubletouch.vendasup.domain.OauthAccess;

/**
 * Created by LADAIR on 09/02/2015.
 */
public class OauthController {

    public static OauthAccess getAccessToken() throws IOException, SyncronizationException {

        if( VendasUp.getOauthAccess() == null ) {
            Credenciais credenciais = new Credenciais( VendasUp.getUsuarioLogado().getEmail(), VendasUp.getUsuarioLogado().getEmail() );
            OauthApi oauthApi = new OauthApi();
            OauthAccess oauthAccess = oauthApi.getAccessToken(credenciais);
            VendasUp.setOauthAccess(oauthAccess);
        }

        return VendasUp.getOauthAccess();

    }


}
