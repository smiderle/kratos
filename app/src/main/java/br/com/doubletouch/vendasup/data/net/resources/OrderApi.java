package br.com.doubletouch.vendasup.data.net.resources;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.mapper.OrderEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Created by LADAIR on 04/05/2015.
 */
public class OrderApi extends AbstractApi {

    public ApiResponse<ServiceResponse<List<Order>>> add( List<Order> orders ) throws IOException, SyncronizationException {

        OrderEntityJsonMaper orderEntityJsonMaper = new OrderEntityJsonMaper();
        RestClient restClient = new RestClient(Endpoints.ENDPOINT_ORDER, Methods.PRODUTO_ORDER_LIST);

        Gson gson = new Gson();
        String customerJson = gson.toJson(orders);

        RESTResponse response = restClient.post(customerJson);

        if( response.getCode() == 200 ) {

            String json = response.getJson();

            ApiResponse<ServiceResponse<List<Order>>> apiResponse =  orderEntityJsonMaper.transformOrderCollection(json);

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
