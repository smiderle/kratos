package br.com.doubletouch.vendasup.data.net.resources;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.mapper.UserEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class PublicApi extends  AbstractApi {

    public ApiResponse<ServiceResponse<User>> getUserByEmailAndPassword(String email, String password, UserEntityJsonMaper userEntityJsonMaper) throws IOException, SyncronizationException {

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_SIGNIN, Methods.PUBLIC_GET_USER_BY_EMAIL_AND_PASSWORD);
        restClient.setParameter("email", email);
        restClient.setParameter("password", password);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<ServiceResponse<User>> apiResponse = userEntityJsonMaper.transformUser( response.getJson());

            if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                return apiResponse;
            } else {
                throw new SyncronizationException(apiResponse.getMessage());
            }
        } else {
            throw new SyncronizationException(response.getException());
        }

    }

    public ApiResponse<ServiceResponse<User>> generateNewUser2(String organizationName, String userName, String email, String password, UserEntityJsonMaper userEntityJsonMaper) throws IOException, SyncronizationException {

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_SIGNIN, Methods.PUBLIC_GENERATE_NEW_USER);
        restClient.setParameter("organizationName", organizationName);
        restClient.setParameter("userName", userName);
        restClient.setParameter("email", email);
        restClient.setParameter("password", password);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<ServiceResponse<User>> apiResponse = userEntityJsonMaper.transformUser( response.getJson());

            if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                return apiResponse;
            } else {
                throw new SyncronizationException(apiResponse.getMessage());
            }
        } else {
            throw new SyncronizationException(response.getException());
        }

    }

    public ApiResponse<ServiceResponse<User>> generateNewUser(String organizationName, String userName, String email, String password, UserEntityJsonMaper userEntityJsonMaper) throws IOException, SyncronizationException {

        RestClient restClient = new RestClient();

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add( new BasicNameValuePair( "password", password ) );
        nameValuePairs.add( new BasicNameValuePair( "email", email ) );
        nameValuePairs.add( new BasicNameValuePair( "organizationName", organizationName ) );
        nameValuePairs.add( new BasicNameValuePair( "userName", userName ) );

        RESTResponse response = restClient.post( Endpoints.ENDPOINT_SIGNIN.concat("/").concat( Methods.PUBLIC_GENERATE_NEW_USER ), nameValuePairs );

        if (response.getCode() == 200) {
            ApiResponse<ServiceResponse<User>> apiResponse = userEntityJsonMaper.transformUser( response.getJson());

            if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                return apiResponse;
            } else {
                throw new SyncronizationException(apiResponse.getMessage());
            }
        } else {
            throw new SyncronizationException(response.getException());
        }

    }

}
