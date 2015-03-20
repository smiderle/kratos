package br.com.doubletouch.vendasup.presentation.view;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 *
 * Created by LADAIR on 15/02/2015.
 */
public interface ProductDetailsView extends  LoadDataView {

    /**
     * Renderiza o produto na tela.
     * @param product
     */
    void renderProduct(Product product);

    void productSaved();
}
