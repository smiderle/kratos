package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class UserEntityJsonMaper {


    private final Gson gson;


    public UserEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<User>>> transformUserCollection(String userListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<User>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<User>>> apiResponse = new Gson().fromJson( userListJsonResponse, serviceType);
        return apiResponse;
    }



    public ApiResponse<ServiceResponse<User>> transformUser(String organizationJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<User>>>() {}.getType();
        ApiResponse<ServiceResponse<User>> apiResponse = new Gson().fromJson( organizationJsonResponse, serviceType);
        return apiResponse;
    }
}
