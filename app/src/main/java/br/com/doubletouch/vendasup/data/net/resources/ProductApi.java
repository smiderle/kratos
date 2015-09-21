package br.com.doubletouch.vendasup.data.net.resources;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.mapper.ProductEntityJsonMaper;
import br.com.doubletouch.vendasup.exception.SyncronizationException;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.util.GsonUtil;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class ProductApi {


    /*public List<Produto> getAllByChangeGreaterThan(Long ultimaSincronizacao, Integer offset) throws Exception {
        List<Produto> produtos = null;

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_PRODUTO, Methods.PRODUTO_GET_ALL_BY_CHANGE_GREATER_THAN );
        restClient.setParameter("date", ultimaSincronizacao);
        restClient.setParameter("organizationID", Kratos.getUsuarioLogado().getOrganizationID());
        restClient.setParameter("offset", offset);

        RESTResponse response = restClient.get();

        if( response.getCode() == 200 ){
            ApiResponse<ServiceResponse<List<Produto>>> apiResponse = new GsonUtil<Produto>().parse(response.getJson());

            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ){
                produtos = apiResponse.getPayload().getValue();
            } else {
                throw new SyncronizationException( apiResponse.getMessage() );
            }
        } else {
            throw new SyncronizationException( response.getMessage() );
        }

        return produtos;
    }*/


    public  ApiResponse<ServiceResponse<List<Product>>> add( List<Product> products ) throws IOException, SyncronizationException {

        ProductEntityJsonMaper productEntityJsonMaper = new ProductEntityJsonMaper();
        RestClient restClient = new RestClient(Endpoints.ENDPOINT_PRODUTO, Methods.PRODUTO_SAVE_LIST);

        Gson gson = new Gson();
        String productsJson = gson.toJson(products);

        RESTResponse response = restClient.post(productsJson);

        if( response.getCode() == 200 ) {

            String json = response.getJson();

            ApiResponse<ServiceResponse<List<Product>>> apiResponse =  productEntityJsonMaper.transformProductCollection(json);

            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ){

                return apiResponse;

            } else {

                throw new SyncronizationException( apiResponse.getMessage() );

            }
        } else {
            throw new SyncronizationException( response.getException() );
        }

    }

    public  ApiResponse<ServiceResponse<List<Product>>> update( List<Product> products ) throws IOException, SyncronizationException {

        ProductEntityJsonMaper productEntityJsonMaper = new ProductEntityJsonMaper();
        RestClient restClient = new RestClient(Endpoints.ENDPOINT_PRODUTO, Methods.PRODUTO_UPDATE_LIST);

        Gson gson = new Gson();
        String productsJson = gson.toJson(products);

        RESTResponse response = restClient.post(productsJson);

        if( response.getCode() == 200 ) {

            String json = response.getJson();

            ApiResponse<ServiceResponse<List<Product>>> apiResponse =  productEntityJsonMaper.transformProductCollection(json);

            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ){

                return apiResponse;

            } else {

                throw new SyncronizationException( apiResponse.getMessage() );

            }
        } else {
            throw new SyncronizationException( response.getException() );
        }

    }

    public  ApiResponse<ServiceResponse<List<Product>>> getAllByChangeGreaterThan(Long ultimaSincronizacao, Integer organizationId, Integer offset,ProductEntityJsonMaper productEntityJsonMaper) throws IOException, SyncronizationException {
        List<Product> products = null;

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_PRODUTO, Methods.PRODUTO_GET_ALL_BY_CHANGE_GREATER_THAN );
        restClient.setParameter("date", ultimaSincronizacao);
        restClient.setParameter("organizationID", organizationId);
        restClient.setParameter("offset", offset);

        RESTResponse response = restClient.get();

        if( response.getCode() == 200 ){
            ApiResponse<ServiceResponse<List<Product>>> apiResponse = productEntityJsonMaper.transformProductCollection(response.getJson());
            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ){
                return apiResponse;
            } else {
                throw new SyncronizationException( apiResponse.getMessage() );
            }
        } else {
            throw new SyncronizationException( response.getException() );
        }
    }

}
