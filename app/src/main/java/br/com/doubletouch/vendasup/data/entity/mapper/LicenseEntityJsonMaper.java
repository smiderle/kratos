package br.com.doubletouch.vendasup.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class LicenseEntityJsonMaper {

    private final Gson gson;

    public LicenseEntityJsonMaper() {
        this.gson = new Gson();
    }

    public ApiResponse<License> transformLicenseCollection(String licenseJsonResponse){
        Type serviceType = new TypeToken<ApiResponse<License>>() {}.getType();
        ApiResponse<License> apiResponse = new Gson().fromJson( licenseJsonResponse, serviceType);
        return apiResponse;
    }
}
