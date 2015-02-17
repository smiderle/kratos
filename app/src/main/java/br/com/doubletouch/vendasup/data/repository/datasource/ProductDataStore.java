package br.com.doubletouch.vendasup.data.repository.datasource;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Interface que representa um data store do local onde os dados são recuperados.
 * Created by LADAIR on 12/02/2015.
 */
public interface ProductDataStore {

    /**
     * Callback usado para os clients serem notificados quando uma lista de produtos for carregado ou ocorrer algum erro
     */
    interface  ProductListCallback {
        void onProductListLoaded(Collection<Product> productsCollection);

        void onError(Exception exception);
    }

    interface  ProductListFilterCallback {
        void onProductListFilterLoaded(Collection<Product> productsCollection);

        void onError(Exception exception);
    }

    /**
      * Callback usado para os clients serem notificados quando um produto for carregado ou ocorrer algum erro
     */
    interface  ProductDetailsCallback {
        void onProductLoaded(Product product);

        void onError(Exception exception);
    }

    /**
     * Obtem um a coleção de {@link br.com.doubletouch.vendasup.data.entity.Product}.
     * @param  branchId Id da Filial
     * @param productListCallback  O {@link br.com.doubletouch.vendasup.domain.repository.ProductRepository.ProductListCallback} usado para notificar os clients.
     */
    void getProductList(Integer branchId, ProductListCallback productListCallback);


    void getProductListFilter(String description, String productId,Integer branchId, ProductListFilterCallback productListFilterCallback);

    /**
     *
     * Obtem um {@link br.com.doubletouch.vendasup.data.entity.Product} pelo id.
     * @param productId O id do produto que sera carregado
     * @param productDetailsCallback O {@link br.com.doubletouch.vendasup.domain.repository.ProductRepository.ProductDetailsCallback} usado para notificar os clients.
     */
    void getProductDetails(final Integer productId, ProductDetailsCallback productDetailsCallback);
}
