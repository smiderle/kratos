package br.com.doubletouch.vendasup.data.net.resources;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.entity.mapper.CustomerEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.LicenseEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class LicenseApi extends AbstractApi {

    public ApiResponse<License> getByUserId(Integer userId) throws IOException, SyncronizationException {

        LicenseEntityJsonMaper licenseEntityJsonMaper = new LicenseEntityJsonMaper();

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_LICENSE, Methods.LICENSE_GET_BY_USER_ID);
        restClient.setParameter("userId", userId);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<License> apiResponse = licenseEntityJsonMaper.transformLicenseCollection( response.getJson());

            if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                return apiResponse;
            } else {
                throw new SyncronizationException(apiResponse.getMessage());
            }
        } else {
            throw new SyncronizationException(response.getException());
        }
    }

    public ApiResponse<License> getAllById(Integer id) throws IOException, SyncronizationException {

        LicenseEntityJsonMaper licenseEntityJsonMaper = new LicenseEntityJsonMaper();

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_LICENSE, Methods.LICENSE_GET_BY_ID);
        restClient.setParameter("id", id);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<License> apiResponse = licenseEntityJsonMaper.transformLicenseCollection( response.getJson());

            if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                return apiResponse;
            } else {
                throw new SyncronizationException(apiResponse.getMessage());
            }
        } else {
            throw new SyncronizationException(response.getException());
        }
    }

}
