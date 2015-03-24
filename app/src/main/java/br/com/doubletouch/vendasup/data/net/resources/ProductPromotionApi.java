package br.com.doubletouch.vendasup.data.net.resources;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.ProductPromotion;
import br.com.doubletouch.vendasup.data.entity.mapper.ProductPromotionEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.ApiResponse;
import br.com.doubletouch.vendasup.data.net.Endpoints;
import br.com.doubletouch.vendasup.data.net.Methods;
import br.com.doubletouch.vendasup.data.net.RESTResponse;
import br.com.doubletouch.vendasup.data.net.RestClient;
import br.com.doubletouch.vendasup.data.net.ServiceResponse;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Created by LADAIR on 27/01/2015.
 */
public class ProductPromotionApi {




    public  ApiResponse<ServiceResponse<List<ProductPromotion>>> getAllByChangeGreaterThan(Long ultimaSincronizacao, Integer offset,Integer organizationId, ProductPromotionEntityJsonMaper productEntityJsonMaper) throws IOException, SyncronizationException {

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_PRODUCT_PROMOTION, Methods.PRODUCT_PROMOTION_GET_ALL_BY_CHANGE_GREATER_THAN );
        restClient.setParameter("date", ultimaSincronizacao);
        restClient.setParameter("organizationID", organizationId);
        restClient.setParameter("offset", offset);

        RESTResponse response = restClient.get();

        if( response.getCode() == 200 ){
            ApiResponse<ServiceResponse<List<ProductPromotion>>> apiResponse = productEntityJsonMaper.transformProductCollection(response.getJson());
            if( apiResponse.getCode().equals( ApiResponse.CODE_SUCESS ) ){
                return apiResponse;
            } else {
                throw new SyncronizationException( apiResponse.getMessage() );
            }
        } else {
            throw new SyncronizationException( response.getMessage() );
        }
    }
}
