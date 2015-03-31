package br.com.doubletouch.vendasup.data.net;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.controller.OauthController;
import br.com.doubletouch.vendasup.exception.SyncronizationException;
import br.com.doubletouch.vendasup.domain.OauthAccess;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class RestClient {

    public RestClient() {
    }

    public RestClient(String resource, String method) {
        this.resource = resource;
        this.method = method;
    }

    /**
     * Parametros que serão enviados no GET
     */
    private LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

    /**
     * Resource
     */
    private String resource = null;

    /**
     * Metodo
     */
    private String method = null;

    public void setResource(String resource){
        this.resource = resource;
    }

    public void setMethod(String method){
        this.method = method;
    }

    public void setParameter(String parameter, Integer value){
        setParameter(parameter, String.valueOf(value));
    }

    public void setParameter(String parameter, Long value){
        setParameter(parameter, String.valueOf(value));
    }

    public void setParameter(String parameter, String value){
        parameters.put(parameter, value);
    }

    private String getUrl(){
        StringBuilder parametrosUrl = new StringBuilder();

        Set<String> parametros = parameters.keySet();
        for (String parametro : parametros) {
            parametrosUrl.append(parametro);
            parametrosUrl.append("=");
            parametrosUrl.append(parameters.get(parametro));
            parametrosUrl.append("&");
        }
        //Remove o ultimo &
        if(parametrosUrl.length() > 0){
            parametrosUrl.deleteCharAt(parametrosUrl.length()-1);
            return resource.concat("/").concat(method).concat("?").concat(parametrosUrl.toString());
        } else {
            return resource.concat("/").concat(method);
        }

    }

    public final RESTResponse get() throws IOException, SyncronizationException {
        if(resource == null || method == null) {
            throw new IllegalArgumentException("É preciso definir o resource e o method");
        }

        String url = getUrl();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;
        RESTResponse restResponse = null;

        httpget.setHeader("Authorization", "Bearer ".concat( OauthController.getAccessToken().getValue()  ));
        response = HttpClientSingleton.getHttpClientInstace().execute(httpget);
        HttpEntity entity = response.getEntity();


        if( !validateOauth( response ) ) {

            httpget.setHeader("Authorization", "Bearer ".concat( OauthController.getAccessToken().getValue() ));
            response = HttpClientSingleton.getHttpClientInstace().execute(httpget);
            entity = response.getEntity();

        }

        if (entity != null) {
            restResponse = new RESTResponse(response.getStatusLine().getStatusCode(), entity.getContent());
        }

        return restResponse;
    }

    public final RESTResponse post(String json) {

        if(resource == null || method == null) {
            throw new IllegalArgumentException("É preciso definir o resource e o method");
        }

        RESTResponse restResponse = null;

        try {


            HttpPost httpPost = new HttpPost(new URI(getUrl()));
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setHeader("userID", VendasUp.getUser().getUserID()+"");
            httpPost.setHeader("Authorization", "Bearer ".concat( OauthController.getAccessToken().getValue() ) );
            StringEntity sEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(sEntity);

            HttpResponse response;
            response = HttpClientSingleton.getHttpClientInstace().execute(httpPost);
            HttpEntity entity = response.getEntity();

            if( !validateOauth( response ) ) {

                httpPost.setHeader("Authorization", "Bearer ".concat( OauthController.getAccessToken().getValue() ) );
                response = HttpClientSingleton.getHttpClientInstace().execute(httpPost);
                entity = response.getEntity();

            }

            if (entity != null) {
                restResponse = new RESTResponse(response.getStatusLine().getStatusCode(), entity.getContent());
            }


            Log.i(VendasUp.APP_TAG, response.toString());

        } catch (Exception e) {
            Log.e("KRATOS", "Falha ao acessar Web service com o metodo POST", e);
            restResponse = new RESTResponse(0, e.getMessage());
        }
        return restResponse;
    }

    public final RESTResponse post(String url, List<NameValuePair> nameValuePairs)  {

        RESTResponse restResponse = null;

        try{

            HttpPost httpPost = new HttpPost(new URI(url));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = HttpClientSingleton.getHttpClientInstace().execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                restResponse = new RESTResponse(response.getStatusLine().getStatusCode(), entity.getContent());
            }

        } catch (Exception e) {
            Log.e("KRATOS", "Falha ao acessar Web service com o metodo POST", e);
            restResponse = new RESTResponse(0, "Ocorreu algum problema. Tente novamente em alguns instantes.");
        }

        return restResponse;
    }

    private String toString(InputStream is) throws IOException {

        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }

    private boolean validateOauth( HttpResponse response ) throws IOException, SyncronizationException {

        boolean isValid = true;

        if( response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED ){
            isValid = false;
            OauthController.refreshToken();

        }
        return isValid;
    }
}
