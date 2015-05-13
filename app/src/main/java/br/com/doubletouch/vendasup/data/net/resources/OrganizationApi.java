package br.com.doubletouch.vendasup.data.net.resources;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Organization;
import br.com.doubletouch.vendasup.data.entity.mapper.OrganizationEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class OrganizationApi extends  AbstractApi {


    public ApiResponse<ServiceResponse<Organization>> getOrganizationById(Long ultimaSincronizacao, Integer organizationID,  OrganizationEntityJsonMaper organizationEntityJsonMaper) throws IOException, SyncronizationException {

        List<Customer> products = null;

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_ORGANIZATION, Methods.ORGANIZATION_GET_BY_ID);
        restClient.setParameter("date", ultimaSincronizacao);
        restClient.setParameter("organizationID", organizationID);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<ServiceResponse<Organization>> apiResponse = organizationEntityJsonMaper.transformOrganization(response.getJson());

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
