package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Organization;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class OrganizationEntityJsonMaper {


    private final Gson gson;


    public OrganizationEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<ServiceResponse<Organization>> transformOrganization(String organizationJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<ServiceResponse<Organization>>>() {}.getType();
        ApiResponse<ServiceResponse<Organization>> apiResponse = new Gson().fromJson( organizationJsonResponse, serviceType);
        return apiResponse;
    }
}
