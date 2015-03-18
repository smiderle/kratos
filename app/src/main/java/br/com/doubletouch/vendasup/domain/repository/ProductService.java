package br.com.doubletouch.vendasup.domain.repository;

import java.util.Collection;

import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.Product}
 * Created by LADAIR on 12/02/2015.
 *
 */
public interface ProductService {

    /**
     * Callback usado para ser notificado quando uma lista de produto for carregada ou um erro ocorrer.
     */
    interface ProductListCallback {
        void onProductListLoaded(Collection<Product> productsCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface ProductListFilterCallback {
        void onProductListFilterLoaded(Collection<Product> productsCollection);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Callback usado para ser notificado quando um produto for carregado ou um erro ocorrer.
     */
    interface  ProductDetailsCallback {
        void onProductLoaded(Product product);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Obtem um a coleção de {@link br.com.doubletouch.vendasup.data.entity.Product}.
     * @param productListCallback  O {@link ProductService.ProductListCallback} usado para notificar os clients.
     */
    void getProductList( ProductListCallback productListCallback);


    void getProductListByFilter(String description, String productId,  ProductListFilterCallback productListFilterCallback);

    /**
     *
     * Obtem um {@link br.com.doubletouch.vendasup.data.entity.Product} pelo id.
     * @param productId O id do produto que sera carregado
     * @param productDetailsCallback O {@link ProductService.ProductDetailsCallback} usado para notificar os clients.
     */
    void getProductById(final int productId, ProductDetailsCallback productDetailsCallback);
}
