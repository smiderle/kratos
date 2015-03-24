package br.com.doubletouch.vendasup.data.net.resources;

import java.io.IOException;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Goal;
import br.com.doubletouch.vendasup.data.entity.mapper.GoalEntityJsonMaper;
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
public class GoalApi extends  AbstractApi {


    public ApiResponse<ServiceResponse<List<Goal>>> getAllByChangeGreaterThan(Long ultimaSincronizacao,Integer organizationId, Integer offset, GoalEntityJsonMaper goalEntityJsonMaper) throws IOException, SyncronizationException {

        RestClient restClient = new RestClient(Endpoints.ENDPOINT_GOAL, Methods.GOAL_GET_ALL_BY_CHANGE_GREATER_THAN);
        restClient.setParameter("date", ultimaSincronizacao);
        restClient.setParameter("organizationID", organizationId);
        restClient.setParameter("offset", offset);

        RESTResponse response = restClient.get();

        if (response.getCode() == 200) {
            ApiResponse<ServiceResponse<List<Goal>>> apiResponse = goalEntityJsonMaper.transformGoalCollection(response.getJson());

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
