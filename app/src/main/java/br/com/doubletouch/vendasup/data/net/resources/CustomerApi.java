package br.com.doubletouch.vendasup.data.net.resources;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.mapper.CustomerEntityJsonMaper;
import br.com.doubletouch.vendasup.data.exception.NetworkConnectionException;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;
import br.com.doubletouch.vendasup.util.GsonUtil;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class CustomerApi extends AbstractApi {

    public ApiResponse<ServiceResponse<List<Customer>>> getAllByChangeGreaterThan(Long ultimaSincronizacao,Integer organizationId, Integer offset, CustomerEntityJsonMaper customerEntityJsonMaper) throws IOException, SyncronizationException {

        List<Customer> products = null;

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_CUSTOMER, Methods.CUSTOMER_GET_ALL_BY_CHANGE_GREATER_THAN);
        restClient.setParameter("date", ultimaSincronizacao);
        restClient.setParameter("organizationID",organizationId);
        restClient.setParameter("offset", offset);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<ServiceResponse<List<Customer>>> apiResponse = customerEntityJsonMaper.transformCustomerCollection(response.getJson());

            if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                return apiResponse;
            } else {
                throw new SyncronizationException(apiResponse.getMessage());
            }
        } else {
            throw new SyncronizationException(response.getException());
        }
    }

        public  ApiResponse<ServiceResponse<List<Customer>>> add( List<Customer> customers ) throws IOException, SyncronizationException {

            CustomerEntityJsonMaper customerEntityJsonMaper = new CustomerEntityJsonMaper();
            RestClient restClient = new RestClient(Endpoints.ENDPOINT_CUSTOMER, Methods.PRODUTO_CUSTOMER_LIST);

            Gson gson = new Gson();
            String customerJson = gson.toJson(customers);

            RESTResponse response = restClient.post(customerJson);

            if( response.getCode() == 200 ) {

                String json = response.getJson();

                ApiResponse<ServiceResponse<List<Customer>>> apiResponse =  customerEntityJsonMaper.transformCustomerCollection(json);

                if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ) {

                    return apiResponse;

                } else {

                    throw new SyncronizationException( apiResponse.getMessage() );

                }
            } else {
                throw new SyncronizationException( response.getException() );
            }

    }


    public  ApiResponse<ServiceResponse<List<Customer>>> update( List<Customer> customers ) throws IOException, SyncronizationException {

        CustomerEntityJsonMaper customerEntityJsonMaper = new CustomerEntityJsonMaper();
        RestClient restClient = new RestClient(Endpoints.ENDPOINT_CUSTOMER, Methods.CUSTOMER_UPDATE_LIST);

        Gson gson = new Gson();
        String customerJson = gson.toJson(customers);

        RESTResponse response = restClient.post(customerJson);

        if( response.getCode() == 200 ) {

            String json = response.getJson();

            ApiResponse<ServiceResponse<List<Customer>>> apiResponse =  customerEntityJsonMaper.transformCustomerCollection(json);

            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ) {

                return apiResponse;

            } else {

                throw new SyncronizationException( apiResponse.getMessage() );

            }
        } else {
            throw new SyncronizationException( response.getException() );
        }

    }
}
