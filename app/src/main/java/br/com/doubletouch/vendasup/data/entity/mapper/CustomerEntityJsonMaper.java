package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class CustomerEntityJsonMaper {


    private final Gson gson;


    public CustomerEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<Customer>>> transformCustomerCollection(String customerListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<Customer>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<Customer>>> apiResponse = new Gson().fromJson( customerListJsonResponse, serviceType);
        return apiResponse;
    }
}
