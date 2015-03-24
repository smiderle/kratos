package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Goal;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class GoalEntityJsonMaper {


    private final Gson gson;


    public GoalEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<List<Goal>>> transformGoalCollection(String userListJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<List<Goal>>>>() {}.getType();
        ApiResponse<ServiceResponse<List<Goal>>> apiResponse = new Gson().fromJson( userListJsonResponse, serviceType);
        return apiResponse;
    }
}
