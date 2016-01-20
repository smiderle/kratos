package br.com.doubletouch.vendasup.data.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.Organization;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.data.service.BranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.data.service.OrganizationServiceImpl;
import br.com.doubletouch.vendasup.data.service.PriceTableServiceImpl;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.data.service.UserBranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.UserServiceImpl;


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
