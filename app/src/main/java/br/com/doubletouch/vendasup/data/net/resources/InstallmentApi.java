package br.com.doubletouch.vendasup.data.net.resources;

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

    public ApiResponse<ServiceResponse<List<Installment>>> getAllByChangeGreaterThan(Long ultimaSincronizacao, Integer offset, InstallmentEntityJsonMaper installmentEntityJsonMaper) throws IOException, SyncronizationException {

            List<Customer> products = null;

            RestClient restClient = new RestClient(Endpoints.ENDPOINT_INSTALLMENT, Methods.INSTALLMENT_GET_ALL_BY_CHANGE_GREATER_THAN);
            restClient.setParameter("date", ultimaSincronizacao);
            restClient.setParameter("organizationID", VendasUp.getUsuarioLogado().getOrganizationID());
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
                throw new SyncronizationException(response.getMessage());
            }

    }
}
