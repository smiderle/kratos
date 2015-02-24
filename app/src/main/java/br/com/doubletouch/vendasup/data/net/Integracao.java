package br.com.doubletouch.vendasup.data.net;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.controller.CustomerController;
import br.com.doubletouch.vendasup.controller.ProdutoController;
import br.com.doubletouch.vendasup.controller.SincronizacaoController;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.mapper.CustomerEntityJsonMaper;
import br.com.doubletouch.vendasup.data.entity.mapper.ProductEntityJsonMaper;
import br.com.doubletouch.vendasup.data.net.resources.CustomerApi;
import br.com.doubletouch.vendasup.data.net.resources.ProductApi;


/**
 * Created by LADAIR on 09/02/2015.
 */
public class Integracao {


    public void receberProdutos() throws Exception {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        ProdutoController produtoController = new ProdutoController();
        Long lastSync = sincronizacaoController.findLastSync(Product.ProductDB.TABELA);
        ApiResponse<ServiceResponse<List<Product>>>  apiResponse = null;
        ProductEntityJsonMaper productEntityJsonMaper = new ProductEntityJsonMaper();
        do {
            apiResponse = new ProductApi().getAllByChangeGreaterThan(lastSync, offset, productEntityJsonMaper);


            List<Product> products = (List<Product>) apiResponse.getPayload().getValue();
            produtoController.saveOrUpdate(products);
            size = products.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), Product.ProductDB.TABELA);
    }

    public void receberClientes() throws Exception {

        Integer offset = 0;
        Integer size = 0;
        SincronizacaoController sincronizacaoController = new SincronizacaoController();
        CustomerController produtoController = new CustomerController();
        Long lastSync = sincronizacaoController.findLastSync(Customer.CustomerDB.TABELA);
        ApiResponse<ServiceResponse<List<Customer>>>  apiResponse = null;
        CustomerEntityJsonMaper customerEntityJsonMaper = new CustomerEntityJsonMaper();
        do {
            apiResponse = new CustomerApi().getAllByChangeGreaterThan(lastSync, offset, customerEntityJsonMaper);

            List<Customer> customers = (List<Customer>) apiResponse.getPayload().getValue();
            Log.i("KRATOS", "INICIOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            produtoController.saveOrUpdate(customers);
            size = customers.size();
            offset += size;
            Log.i("KRATOS", "FINALIZOU O INSERT" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), Product.ProductDB.TABELA);
    }
}
