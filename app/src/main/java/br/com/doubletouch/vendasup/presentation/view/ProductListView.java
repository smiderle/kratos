package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 10/02/2015.
 */
public interface ProductListView extends  LoadDataView {


    /**
     * Interface para "ouvir" os eventos da lista de produtos.
     */
    public interface ProductListListener {
    }


    /**
     * Renderiza a lista de produtos na tela.
     * @param products
     */
    void renderProductList(List<Product> products);

    /**
     * Visualiza detalhes do {@link br.com.doubletouch.vendasup.data.entity.Product}
     * @param product
     */
    void viewProduct(Product product);
}
