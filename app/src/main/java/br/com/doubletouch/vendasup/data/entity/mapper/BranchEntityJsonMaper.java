package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class BranchEntityJsonMaper {


    private final Gson gson;


    public BranchEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<BranchOffice>>> transformBranchCollection(String branchListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<BranchOffice>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<BranchOffice>>> apiResponse = new Gson().fromJson( branchListJsonResponse, serviceType);
        return apiResponse;
    }
}
