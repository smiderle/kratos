package br.com.doubletouch.vendasup.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class GsonUtil<T> {

    public static <T> T parse(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        return gson.fromJson(obj, classOfT);
    }

    public ApiResponse<ServiceResponse<List<T>>> parse(String json){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<Product>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<T>>> apiResponse = new Gson().fromJson( json, serviceType);
        return apiResponse;
    }


}
