package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;

import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class InstallmentEntityJsonMaper {


    private final Gson gson;


    public InstallmentEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<Installment>>> transformInstallmentCollection(String installmentListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<Installment>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<Installment>>> apiResponse = new Gson().fromJson( installmentListJsonResponse, serviceType);
        return apiResponse;
    }
}
