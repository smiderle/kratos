package br.com.doubletouch.vendasup.data.entity.mapper;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class ProductEntityJsonMaper {

    private final Gson gson;


    public ProductEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<Product>>> transformProductCollection(String productListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<Product>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<Product>>> apiResponse = new Gson().fromJson( productListJsonResponse, serviceType);
        return apiResponse;
    }
}
