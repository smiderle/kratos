package br.com.doubletouch.vendasup.data.net.resources;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.exception.SyncronizationException;
import br.com.doubletouch.vendasup.domain.Credenciais;
import br.com.doubletouch.vendasup.domain.OauthAccess;
import br.com.doubletouch.vendasup.util.GsonUtil;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;

/**
 * Webservice responsavel pela geração de tokens
 *
 * Created by LADAIR on 27/01/2015.
 */
public class OauthApi {

    public OauthAccess getAccessToken( Credenciais credenciais ) throws IOException, SyncronizationException {

        OauthAccess oauthAccess= new OauthAccess();

        RestClient restClient = new RestClient();
        RESTResponse response = null;

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add( new BasicNameValuePair( "password", credenciais.getPassword() ) );
        nameValuePairs.add( new BasicNameValuePair( "username", credenciais.getUser() ) );
        nameValuePairs.add( new BasicNameValuePair( "grant_type", "password" ) );
        nameValuePairs.add( new BasicNameValuePair( "client_secret", credenciais.getClienteSecret() ) );
        nameValuePairs.add( new BasicNameValuePair( "client_id", credenciais.getClienteID() ) );
        nameValuePairs.add( new BasicNameValuePair( "last", "1" ) );

        response = restClient.post( Endpoints.ENDPOINT_OAUTH, nameValuePairs );

        if( response.getCode() == 200 ){
            return GsonUtil.parse( response.getJson(), OauthAccess.class );
        } else {
            throw new SyncronizationException( response.getMessage() );
        }


    }
}
