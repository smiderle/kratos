package br.com.doubletouch.vendasup.data.net.resources;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.mapper.PriceTableEntityJsonMaper;
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
public class PriceTableApi extends AbstractApi {

    public ApiResponse<ServiceResponse<List<PriceTable>>> getAllByChangeGreaterThan(Long ultimaSincronizacao, Integer organizationId, Integer offset,PriceTableEntityJsonMaper priceTableEntityJsonMaper) throws IOException, SyncronizationException {

            List<Customer> products = null;

            RestClient restClient = new RestClient(Endpoints.ENDPOINT_PRICE_TABLE, Methods.PRICE_TABLE_GET_ALL_BY_CHANGE_GREATER_THAN);
            restClient.setParameter("date", ultimaSincronizacao);
            restClient.setParameter("organizationID", organizationId);
            restClient.setParameter("offset", offset);

            RESTResponse response = restClient.get();

            if (response.getCode() == 200) {
                ApiResponse<ServiceResponse<List<PriceTable>>> apiResponse = priceTableEntityJsonMaper.transformPriceTableCollection(response.getJson());

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
