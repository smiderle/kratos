package br.com.doubletouch.vendasup.data.net;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.controller.CustomerController;
import br.com.doubletouch.vendasup.controller.PriceTableController;
import br.com.doubletouch.vendasup.controller.ProdutoController;
import br.com.doubletouch.vendasup.controller.SincronizacaoController;
import br.com.doubletouch.vendasup.data.database.script.InstallmentDB;
import br.com.doubletouch.vendasup.data.database.script.ProductDB;
import br.com.doubletouch.vendasup.data.database.script.PriceTableDB;
import br.com.doubletouch.vendasup.data.database.script.CustomerDB;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.mapper.CustomerEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.InstallmentEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.PriceTableEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.ProductEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.resources.CustomerApi;
import br.com.doubletouch.vendasup.data.net.resources.InstallmentApi;
import br.com.doubletouch.vendasup.data.net.resources.PriceTableApi;
import br.com.doubletouch.vendasup.data.net.resources.ProductApi;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.domain.repository.InstallmentService;
import br.com.doubletouch.vendasup.exception.SyncronizationException;


/**
 * Created by LADAIR on 09/02/2015.
 */
public class Integracao {


    public void receberProdutos() throws Exception {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        ProdutoController produtoController = new ProdutoController();
        Long lastSync = sincronizacaoController.findLastSync(ProductDB.TABELA);
        ApiResponse<ServiceResponse<List<Product>>>  apiResponse = null;
        ProductEntityJsonMaper productEntityJsonMaper = new ProductEntityJsonMaper();
        do {
            apiResponse = new ProductApi().getAllByChangeGreaterThan(lastSync, offset, productEntityJsonMaper);


            List<Product> products =  apiResponse.getPayload().getValue();
            produtoController.saveOrUpdate(products);
            size = products.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), ProductDB.TABELA);
    }

    public void receberClientes() throws Exception {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        CustomerController produtoController = new CustomerController();
        Long lastSync = sincronizacaoController.findLastSync(CustomerDB.TABELA);
        ApiResponse<ServiceResponse<List<Customer>>>  apiResponse = null;
        CustomerEntityJsonMaper customerEntityJsonMaper = new CustomerEntityJsonMaper();
        do {
            apiResponse = new CustomerApi().getAllByChangeGreaterThan(lastSync, offset, customerEntityJsonMaper);

            List<Customer> customers = apiResponse.getPayload().getValue();
            Log.i("KRATOS", "INICIOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            produtoController.saveOrUpdate(customers);
            size = customers.size();
            offset += size;
            Log.i("KRATOS", "FINALIZOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), ProductDB.TABELA);
    }

    public void receberTabelasPrecos() throws IOException, SyncronizationException {
        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(PriceTableDB.TABELA);
        ApiResponse<ServiceResponse<List<PriceTable>>>  apiResponse = null;
        PriceTableEntityJsonMaper priceTableEntityJsonMaper = new PriceTableEntityJsonMaper();
        PriceTableController controller = new PriceTableController();
        do {
            apiResponse = new PriceTableApi().getAllByChangeGreaterThan(lastSync, offset, priceTableEntityJsonMaper);
            List<PriceTable> tabelas =  apiResponse.getPayload().getValue();
            Log.i("KRATOS", "INICIOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

            controller.saveOrUpdate(tabelas);
            size = tabelas.size();
            offset += size;
            Log.i("KRATOS", "FINALIZOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), PriceTableDB.TABELA);

    }



    public void receberParcelamentos() throws IOException, SyncronizationException {
        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        Long lastSync = sincronizacaoController.findLastSync(InstallmentDB.TABELA);
        ApiResponse<ServiceResponse<List<Installment>>>  apiResponse = null;
        InstallmentEntityJsonMaper installmentEntityJsonMaper = new InstallmentEntityJsonMaper();
        InstallmentService service = new InstallmentServiceImpl();
        do {
            apiResponse = new InstallmentApi().getAllByChangeGreaterThan(lastSync, offset, installmentEntityJsonMaper);
            List<Installment> parcelamentos =  apiResponse.getPayload().getValue();
            Log.i("KRATOS", "INICIOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

            service.saveOrUpdateSynchronous(parcelamentos);
            size = parcelamentos.size();
            offset += size;
            Log.i("KRATOS", "FINALIZOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), InstallmentDB.TABELA);

    }
}
