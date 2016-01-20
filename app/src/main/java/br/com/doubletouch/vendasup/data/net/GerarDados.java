package br.com.doubletouch.vendasup.data.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.controller.SincronizacaoController;
import br.com.doubletouch.vendasup.data.database.script.BranchDB;
import br.com.doubletouch.vendasup.data.database.script.CustomerDB;
import br.com.doubletouch.vendasup.data.database.script.GoalDB;
import br.com.doubletouch.vendasup.data.database.script.InstallmentDB;
import br.com.doubletouch.vendasup.data.database.script.OrganizationDB;
import br.com.doubletouch.vendasup.data.database.script.PriceTableDB;
import br.com.doubletouch.vendasup.data.database.script.ProductDB;
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
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.data.entity.Usuario;
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
import br.com.doubletouch.vendasup.data.service.CustomerService;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.data.service.GoalService;
import br.com.doubletouch.vendasup.data.service.GoalServiceImpl;
import br.com.doubletouch.vendasup.data.service.InstallmentService;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.data.service.LicenseService;
import br.com.doubletouch.vendasup.data.service.LicenseServiceImpl;
import br.com.doubletouch.vendasup.data.service.OrderService;
import br.com.doubletouch.vendasup.data.service.OrderServiceImpl;
import br.com.doubletouch.vendasup.data.service.OrganizationService;
import br.com.doubletouch.vendasup.data.service.OrganizationServiceImpl;
import br.com.doubletouch.vendasup.data.service.PriceTableService;
import br.com.doubletouch.vendasup.data.service.PriceTableServiceImpl;
import br.com.doubletouch.vendasup.data.service.ProductPromotionService;
import br.com.doubletouch.vendasup.data.service.ProductPromotionServiceImpl;
import br.com.doubletouch.vendasup.data.service.ProductService;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.data.service.UserBranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.UserService;
import br.com.doubletouch.vendasup.data.service.UserServiceImpl;
import br.com.doubletouch.vendasup.exception.SyncronizationException;


/**
 * Created by LADAIR on 09/02/2015.
 */
public class GerarDados {


    public void receberProdutos( String json ) throws Exception {

        Type serviceType = new TypeToken< List< Product > >() {
        }.getType();
        List< Product > produtos = new Gson().fromJson( json, serviceType );
        new ProductServiceImpl().saveOrUpdateSynchronous( produtos );

    }

    public void receberClientes( String json ) throws Exception {

        Type serviceType = new TypeToken< List< Customer > >() {
        }.getType();
        List< Customer > clientes = new Gson().fromJson( json, serviceType );
        new CustomerServiceImpl().saveOrUpdateSynchronous( clientes );

    }

    public void receberEmpresa( String json ) throws Exception {

        Type serviceType = new TypeToken< List< Organization > >() {
        }.getType();
        List< Organization > empresas = new Gson().fromJson( json, serviceType );
        new OrganizationServiceImpl().saveOrUpdateSynchronous( empresas );

    }

    public void receberUsuarioEmpresa( String json ) throws Exception {

        Type serviceType = new TypeToken< List< UserBranchOffice > >() {
        }.getType();
        List< UserBranchOffice > empresas = new Gson().fromJson( json, serviceType );
        new UserBranchServiceImpl().saveOrUpdateSynchronous( empresas );

    }

    public void receberTabelaPreco( String json ) throws Exception {

        Type serviceType = new TypeToken< List< PriceTable > >() {
        }.getType();
        List< PriceTable > empresas = new Gson().fromJson( json, serviceType );
        new PriceTableServiceImpl().saveOrUpdateSynchronous( empresas );

    }

    public void receberParcelamento( String json ) throws Exception {

        Type serviceType = new TypeToken< List< Installment > >() {
        }.getType();
        List< Installment > empresas = new Gson().fromJson( json, serviceType );
        new InstallmentServiceImpl().saveOrUpdateSynchronous( empresas );

    }

    public void receberFilial( String json ) throws Exception {

        Type serviceType = new TypeToken< List< BranchOffice > >() {
        }.getType();
        List< BranchOffice > empresas = new Gson().fromJson( json, serviceType );
        new BranchServiceImpl().saveOrUpdateSynchronous( empresas );

    }

    public void receberUsuarios( String json ) throws Exception {

        Type serviceType = new TypeToken< List< User > >() {
        }.getType();
        List< User > empresas = new Gson().fromJson( json, serviceType );
        new UserServiceImpl().saveOrUpdateSynchronous( empresas );

    }

}
