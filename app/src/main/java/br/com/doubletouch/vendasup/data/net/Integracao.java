package br.com.doubletouch.vendasup.data.net;

import java.io.IOException;
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
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.entity.Order;
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
import br.com.doubletouch.vendasup.data.net.resources.LicenseApi;
import br.com.doubletouch.vendasup.data.net.resources.OrderApi;
import br.com.doubletouch.vendasup.data.net.resources.OrganizationApi;
import br.com.doubletouch.vendasup.data.net.resources.PriceTableApi;
import br.com.doubletouch.vendasup.data.net.resources.ProductApi;
import br.com.doubletouch.vendasup.data.net.resources.ProductPromotionApi;
import br.com.doubletouch.vendasup.data.net.resources.PublicApi;
import br.com.doubletouch.vendasup.data.net.resources.UserApi;
import br.com.doubletouch.vendasup.data.service.BranchService;
import br.com.doubletouch.vendasup.data.service.BranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.data.service.GoalService;
import br.com.doubletouch.vendasup.data.service.GoalServiceImpl;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.data.service.LicenseService;
import br.com.doubletouch.vendasup.data.service.LicenseServiceImpl;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
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

    public void enviarDados( Integer branchID ) throws IOException, SyncronizationException {

        atualizarParcelamentos(branchID);
        enviarParcelamentos(branchID);
        atualizarClientes(branchID);
        enviarClientes(branchID);
        atualizarProdutos(branchID);
        enviarProdutos( branchID );
        enviarPedidos( branchID );

    }



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

        apiResponse =  new OrganizationApi().getOrganizationById(lastSync, organizationID, organizationEntityJsonMaper);
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

    public void receberLicencaPorUsuario(Integer userID) throws IOException, SyncronizationException {

        LicenseService service = new LicenseServiceImpl();

        ApiResponse<License> apiResponse = new LicenseApi().getByUserId(userID);

        service.save(apiResponse.getPayload());

    }


    public void enviarProdutos(Integer branchID) throws IOException, SyncronizationException {

        ProductService productService = new ProductServiceImpl();

        List<Product> products = productService.getAllSyncPendenteNovos(branchID);

        if( products.size() > 0 ){

            ApiResponse<ServiceResponse<List<Product>>> apiResponse = new ProductApi().add(products);

            List<Product> produtsSaved = apiResponse.getPayload().getValue();

            productService.updateByIdMobile(produtsSaved);

        }


    }

    public void atualizarProdutos(Integer branchID) throws IOException, SyncronizationException {

        ProductService productService = new ProductServiceImpl();

        List<Product> products = productService.getAllSyncPendenteAtualizados(branchID);

        if( products.size() > 0 ){

            new ProductApi().update(products);

            //Na atualização dos produtos, não precisa inserir nada no mobile, depois do retorno.

        }


    }

    public void enviarClientes(Integer branchID) throws  IOException, SyncronizationException {

        CustomerService customerService = new CustomerServiceImpl();

        List<Customer> customers = customerService.getAllSyncPendendenteClientesNovos(branchID);

        if(customers.size() > 0){

            ApiResponse<ServiceResponse<List<Customer>>> apiResponse = new CustomerApi().add(customers);

            List<Customer> customersSaved = apiResponse.getPayload().getValue();

            customerService.updateByIdMobile(customersSaved);

        }

    }

    public void atualizarClientes(Integer branchID) throws  IOException, SyncronizationException {

        CustomerService customerService = new CustomerServiceImpl();

        List<Customer> customers = customerService.getAllSyncPendendenteClientesAtualizados(branchID);

        if(customers.size() > 0){

            new CustomerApi().update(customers);

            //Na atualização dos clientes, não precisa inserir nada no mobile, depois do retorno.

        }

    }

    public void enviarPedidos(Order order, String email) throws  IOException, SyncronizationException {

        new OrderApi().sendEmail(order, email);

    }


    public void enviarPedidos(Integer branchID) throws  IOException, SyncronizationException {

        OrderService orderService = new OrderServiceImpl();

        List<Order> orders = orderService.getAllSyncPending(branchID);

        if(orders.size() > 0){

            ApiResponse<ServiceResponse<List<Order>>> apiResponse = new OrderApi().add(orders);

            List<Order> ordersSaved = apiResponse.getPayload().getValue();

            orderService.updateSync(ordersSaved);

        }

    }

    public User getUserByEmail(String email, String password)  throws  IOException, SyncronizationException {

        UserEntityJsonMaper userEntityJsonMaper = new UserEntityJsonMaper();

        ApiResponse<ServiceResponse<User>> apiResponse = new PublicApi().getUserByEmailAndPassword(email.replaceAll("\\s", ""), password.replaceAll("\\s", ""), userEntityJsonMaper);

        return apiResponse.getPayload().getValue();

    }

    public User generateNewUser(String organizationName, String userName, String email, String password)  throws  IOException, SyncronizationException {

        UserEntityJsonMaper userEntityJsonMaper = new UserEntityJsonMaper();

        ApiResponse<ServiceResponse<User>> apiResponse = new PublicApi().generateNewUser(organizationName, userName, email, password, userEntityJsonMaper);

        return apiResponse.getPayload().getValue();

    }

    public void confirmation( String email )  throws  IOException, SyncronizationException {

        UserEntityJsonMaper userEntityJsonMaper = new UserEntityJsonMaper();

        ApiResponse<ServiceResponse<Boolean>> apiResponse = new PublicApi().confirmation(email, userEntityJsonMaper);

    }

    public void validaCodigo( String email, String codigo )  throws  IOException, SyncronizationException {

        UserEntityJsonMaper userEntityJsonMaper = new UserEntityJsonMaper();

        ApiResponse<ServiceResponse<Boolean>> apiResponse = new PublicApi().validaCodigo(email, codigo, userEntityJsonMaper);

    }




    public void enviarParcelamentos(Integer branchID) throws  IOException, SyncronizationException {

        InstallmentService customerService = new InstallmentServiceImpl();

        List<Installment> installments = customerService.getAllSyncPendenteNovos(branchID);

        if(installments.size() > 0){

            ApiResponse<ServiceResponse<List<Installment>>> apiResponse = new InstallmentApi().add(installments);

            List<Installment> installmentsSaved = apiResponse.getPayload().getValue();

            customerService.updateByIdMobile(installmentsSaved);

        }

    }


    public void atualizarParcelamentos(Integer branchID) throws  IOException, SyncronizationException {

        InstallmentService customerService = new InstallmentServiceImpl();

        List<Installment> installments = customerService.getAllSyncPendenteAtualizados(branchID);

        if(installments.size() > 0){

            new InstallmentApi().update(installments);

        }

    }
}
