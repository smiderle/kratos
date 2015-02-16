package br.com.doubletouch.vendasup.data.net;

import java.util.List;

import br.com.doubletouch.vendasup.controller.ProdutoController;
import br.com.doubletouch.vendasup.controller.SincronizacaoController;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.net.resources.ProdutoApi;


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
        do {
            apiResponse = new ProdutoApi().getAllByChangeGreaterThan(lastSync, offset);

            List<Product> products = (List<Product>) apiResponse.getPayload().getValue();
            produtoController.saveOrUpdate(products);
            size = products.size();
            offset += size;
        } while (size > 0 );

        sincronizacaoController.updateLastSync(apiResponse.getHour(), Product.ProductDB.TABELA);
    }
}
