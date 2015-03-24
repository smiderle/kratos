package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.ProductPromotion;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class ProductPromotionEntityJsonMaper {

    private final Gson gson;


    public ProductPromotionEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<ProductPromotion>>> transformProductCollection(String productListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<ProductPromotion>>>>() {}.getType();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


        ApiResponse<ServiceResponse<List<ProductPromotion>>> apiResponse =  gson.fromJson( productListJsonResponse, serviceType);
        return apiResponse;
    }
}
