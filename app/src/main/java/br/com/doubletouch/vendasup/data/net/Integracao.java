package br.com.doubletouch.vendasup.data.net;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.controller.SincronizacaoController;
import br.com.doubletouch.vendasup.data.database.script.BranchDB;
import br.com.doubletouch.vendasup.data.database.script.GoalDB;
import br.com.doubletouch.vendasup.data.database.script.InstallmentDB;
import br.com.doubletouch.vendasup.data.database.script.OrganizationDB;
import br.com.doubletouch.vendasup.data.database.script.ProductDB;
import br.com.doubletouch.vendasup.data.database.script.PriceTableDB;
import br.com.doubletouch.vendasup.data.database.script.CustomerDB;
import br.com.doubletouch.vendasup.data.database.script.ProductPromotionDB;
import br.com.doubletouch.vendasup.data.database.script.UserDB;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Goal;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.Organization;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.ProductPromotion;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.mapper.BranchEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.CustomerEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.GoalEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.InstallmentEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.OrganizationEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.PriceTableEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.ProductEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.ProductPromotionEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.UserEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.resources.BranchApi;
import br.com.doubletouch.vendasup.data.net.resources.CustomerApi;
import br.com.doubletouch.vendasup.data.net.resources.GoalApi;
import br.com.doubletouch.vendasup.data.net.resources.InstallmentApi;
import br.com.doubletouch.vendasup.data.net.resources.OrganizationApi;
import br.com.doubletouch.vendasup.data.net.resources.PriceTableApi;
import br.com.doubletouch.vendasup.data.net.resources.ProductApi;
import br.com.doubletouch.vendasup.data.net.resources.ProductPromotionApi;
import br.com.doubletouch.vendasup.data.net.resources.UserApi;
import br.com.doubletouch.vendasup.data.service.BranchService;
import br.com.doubletouch.vendasup.data.service.BranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.data.service.GoalService;
import br.com.doubletouch.vendasup.data.service.GoalServiceImpl;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.data.service.OrganizationService;
import br.com.doubletouch.vendasup.data.service.OrganizationServiceImpl;
import br.com.doubletouch.vendasup.data.service.PriceTableServiceImpl;
import br.com.doubletouch.vendasup.data.service.ProductPromotionService;
import br.com.doubletouch.vendasup.data.service.ProductPromotionServiceImpl;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.data.service.CustomerService;
import br.com.doubletouch.vendasup.data.service.InstallmentService;
import br.com.doubletouch.vendasup.data.service.PriceTableService;
import br.com.doubletouch.vendasup.data.service.ProductService;
import br.com.doubletouch.vendasup.data.service.UserService;
import br.com.doubletouch.vendasup.data.service.UserServiceImpl;
import br.com.doubletouch.vendasup.exception.SyncronizationException;


/**
 * Created by LADAIR on 09/02/2015.
 */
public class Integracao {


    public void receberProdutos(Integer organizationId ) throws Exception {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        ProductService productService = new ProductServiceImpl();
        Long lastSync = sincronizacaoController.findLastSync(ProductDB.TABELA);
        ApiResponse<ServiceResponse<List<Product>>>  apiResponse = null;
        ProductEntityJsonMaper productEntityJsonMaper = new ProductEntityJsonMaper();
        do {
            apiResponse = new ProductApi().getAllByChangeGreaterThan(lastSync,  organizationId,offset, productEntityJsonMaper);


            List<Product> products =  apiResponse.getPayload().getValue();
            productService.saveOrUpdateSynchronous(products);
            size = products.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), ProductDB.TABELA);
    }

    public void receberClientes(Integer organizationId ) throws Exception {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        CustomerService customerService = new CustomerServiceImpl();
        Long lastSync = sincronizacaoController.findLastSync(CustomerDB.TABELA);
        ApiResponse<ServiceResponse<List<Customer>>>  apiResponse = null;
        CustomerEntityJsonMaper customerEntityJsonMaper = new CustomerEntityJsonMaper();
        do {
            apiResponse = new CustomerApi().getAllByChangeGreaterThan(lastSync,organizationId,  offset, customerEntityJsonMaper);

            List<Customer> customers = apiResponse.getPayload().getValue();
            customerService.saveOrUpdateSynchronous(customers);
            size = customers.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), CustomerDB.TABELA);
    }

    public void receberTabelasPrecos(Integer organizationId) throws IOException, SyncronizationException {
        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(PriceTableDB.TABELA);
        ApiResponse<ServiceResponse<List<PriceTable>>>  apiResponse = null;
        PriceTableEntityJsonMaper priceTableEntityJsonMaper = new PriceTableEntityJsonMaper();
        PriceTableService priceTableService = new PriceTableServiceImpl();
        do {
            apiResponse = new PriceTableApi().getAllByChangeGreaterThan(lastSync,  organizationId, offset,priceTableEntityJsonMaper);
            List<PriceTable> tabelas =  apiResponse.getPayload().getValue();

            priceTableService.saveOrUpdateSynchronous(tabelas);
            size = tabelas.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), PriceTableDB.TABELA);

    }



    public void receberParcelamentos(Integer organizationId) throws IOException, SyncronizationException {
        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(InstallmentDB.TABELA);
        ApiResponse<ServiceResponse<List<Installment>>>  apiResponse = null;
        InstallmentEntityJsonMaper installmentEntityJsonMaper = new InstallmentEntityJsonMaper();
        InstallmentService service = new InstallmentServiceImpl();
        do {
            apiResponse = new InstallmentApi().getAllByChangeGreaterThan(lastSync, organizationId, offset, installmentEntityJsonMaper);
            List<Installment> parcelamentos =  apiResponse.getPayload().getValue();

            service.saveOrUpdateSynchronous(parcelamentos);
            size = parcelamentos.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), InstallmentDB.TABELA);

    }



    public void receberUsuarios(Integer organizationId) throws IOException, SyncronizationException {
        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(UserDB.TABELA);
        ApiResponse<ServiceResponse<List<User>>>  apiResponse = null;
        UserEntityJsonMaper userEntityJsonMaper = new UserEntityJsonMaper();
        UserService service = new UserServiceImpl();
        do {
            apiResponse = new UserApi().getAllByChangeGreaterThan(lastSync, organizationId, offset, userEntityJsonMaper);
            List<User> users =  apiResponse.getPayload().getValue();

            service.saveOrUpdateSynchronous(users);
            size = users.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), UserDB.TABELA);

    }


    public void receberEmpresa(Integer organizationID) throws IOException, SyncronizationException {
        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(OrganizationDB.TABELA);
        ApiResponse<ServiceResponse<Organization>>  apiResponse = null;
        OrganizationEntityJsonMaper organizationEntityJsonMaper = new OrganizationEntityJsonMaper();
        OrganizationService service = new OrganizationServiceImpl();

        apiResponse =  new OrganizationApi().getAllByChangeGreaterThan(lastSync, organizationID, organizationEntityJsonMaper);
        Organization organization =  apiResponse.getPayload().getValue();
        service.saveOrUpdateSynchronous(organization);

        sincronizacaoController.updateLastSync(apiResponse.getHour(), OrganizationDB.TABELA);

    }

    public void receberFilial(Integer organizationID) throws IOException, SyncronizationException {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(BranchDB.TABELA);
        ApiResponse<ServiceResponse<List<BranchOffice>>>  apiResponse = null;
        BranchEntityJsonMaper branchEntityJsonMaper = new BranchEntityJsonMaper();
        BranchService service = new BranchServiceImpl();

        do {

            apiResponse =  new BranchApi().getAllByChangeGreaterThan(lastSync,offset, organizationID, branchEntityJsonMaper);
            List<BranchOffice> branches =  apiResponse.getPayload().getValue();

            service.saveOrUpdateSynchronous(branches);
            size = branches.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), BranchDB.TABELA);

    }

    public void receberPromocoes(Integer organizationID) throws IOException, SyncronizationException {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(ProductPromotionDB.TABELA);
        ApiResponse<ServiceResponse<List<ProductPromotion>>>  apiResponse = null;
        ProductPromotionEntityJsonMaper productPromotionEntityJsonMaper = new ProductPromotionEntityJsonMaper();
        ProductPromotionService service = new ProductPromotionServiceImpl();

        do {
            apiResponse =  new ProductPromotionApi().getAllByChangeGreaterThan(lastSync,offset, organizationID, productPromotionEntityJsonMaper);
            List<ProductPromotion> branches =  apiResponse.getPayload().getValue();

            service.saveOrUpdateSynchronous(branches);
            size = branches.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), ProductPromotionDB.TABELA);

    }

    public void receberMetas(Integer organizationID) throws IOException, SyncronizationException {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(GoalDB.TABELA);
        ApiResponse<ServiceResponse<List<Goal>>>  apiResponse = null;
        GoalEntityJsonMaper goalEntityJsonMaper = new GoalEntityJsonMaper();
        GoalService service = new GoalServiceImpl();

        do {
            apiResponse =  new GoalApi().getAllByChangeGreaterThan(lastSync,organizationID,offset, goalEntityJsonMaper);
            List<Goal> goals =  apiResponse.getPayload().getValue();

            service.saveOrUpdateSynchronous(goals);
            size = goals.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), GoalDB.TABELA);

    }
}
