package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 04/05/2015.
 */
public class OrderEntityJsonMaper {


    private final Gson gson;


    public OrderEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<Order>>> transformOrderCollection(String orderListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<Order>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<Order>>> apiResponse = new Gson().fromJson( orderListJsonResponse, serviceType);
        return apiResponse;
    }
}
