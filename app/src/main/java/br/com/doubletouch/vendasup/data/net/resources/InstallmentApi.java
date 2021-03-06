package br.com.doubletouch.vendasup.data.net.resources;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.mapper.InstallmentEntityJsonMaper;
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
public class InstallmentApi extends AbstractApi {

    public ApiResponse<ServiceResponse<List<Installment>>> getAllByChangeGreaterThan(Long ultimaSincronizacao, Integer organizationId, Integer offset,InstallmentEntityJsonMaper installmentEntityJsonMaper) throws IOException, SyncronizationException {

            List<Customer> products = null;

            RestClient restClient = new RestClient(Endpoints.ENDPOINT_INSTALLMENT, Methods.INSTALLMENT_GET_ALL_BY_CHANGE_GREATER_THAN);
            restClient.setParameter("date", ultimaSincronizacao);
            restClient.setParameter("organizationID", organizationId);
            restClient.setParameter("offset", offset);

            RESTResponse response = restClient.get();

            if (response.getCode() == 200) {
                ApiResponse<ServiceResponse<List<Installment>>> apiResponse = installmentEntityJsonMaper.transformInstallmentCollection(response.getJson());

                if (apiResponse.getCode().equals(ApiResponse.CODE_SUCESS)) {
                    return apiResponse;
                } else {
                    throw new SyncronizationException(apiResponse.getMessage());
                }
            } else {
                throw new SyncronizationException(response.getException());
            }

    }


    public  ApiResponse<ServiceResponse<List<Installment>>> add( List<Installment> customers ) throws IOException, SyncronizationException {

        InstallmentEntityJsonMaper customerEntityJsonMaper = new InstallmentEntityJsonMaper();
        RestClient restClient = new RestClient(Endpoints.ENDPOINT_INSTALLMENT, Methods.INSALMENT_SAVE_LIST);

        Gson gson = new Gson();
        String customerJson = gson.toJson(customers);

        RESTResponse response = restClient.post(customerJson);

        if( response.getCode() == 200 ) {

            String json = response.getJson();

            ApiResponse<ServiceResponse<List<Installment>>> apiResponse =  customerEntityJsonMaper.transformInstallmentCollection(json);

            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ) {

                return apiResponse;

            } else {

                throw new SyncronizationException( apiResponse.getMessage() );

            }
        } else {
            throw new SyncronizationException( response.getException() );
        }

    }

    public  ApiResponse<ServiceResponse<List<Installment>>> update( List<Installment> customers ) throws IOException, SyncronizationException {

        InstallmentEntityJsonMaper customerEntityJsonMaper = new InstallmentEntityJsonMaper();
        RestClient restClient = new RestClient(Endpoints.ENDPOINT_INSTALLMENT, Methods.INSALMENT_SAVE_LIST);

        Gson gson = new Gson();
        String customerJson = gson.toJson(customers);

        RESTResponse response = restClient.post(customerJson);

        if( response.getCode() == 200 ) {

            String json = response.getJson();

            ApiResponse<ServiceResponse<List<Installment>>> apiResponse =  customerEntityJsonMaper.transformInstallmentCollection(json);

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
