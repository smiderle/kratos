package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class PriceTableEntityJsonMaper {


    private final Gson gson;


    public PriceTableEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<PriceTable>>> transformPriceTableCollection(String priceTableListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<PriceTable>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<PriceTable>>> apiResponse = new Gson().fromJson( priceTableListJsonResponse, serviceType);
        return apiResponse;
    }
}
