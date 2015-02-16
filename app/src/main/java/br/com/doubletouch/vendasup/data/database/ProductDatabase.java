package br.com.doubletouch.vendasup.data.database;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Interface que representa o acesso ao banco de dados para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.Product}
 * Created by LADAIR on 12/02/2015.
 */
public interface ProductDatabase {

    /**
     * Callback usado para notificar quando uma collection de {@link Product} for carregado
     */
    interface ProductListCallback {
        void onProductListLoaded(Collection<Product> productsCollection);

        void onError(Exception exception);
    }

    /**
     * Callback usado para notificar quando o {@link Product} for carregado
     */
    interface ProductDetailsCallback {
        void onProductLoaded(Product product);

        void onError(Exception exception);
    }

    /**
     * Recupera um {@link Product}  do banco de dados
     * @param productId Id do produto
     * @param callback o {@link br.com.doubletouch.vendasup.data.database.ProductDatabase.ProductListCallback} que notificara os clients.
     */
    void get(final Integer productId, final ProductDetailsCallback callback);

    /**
     * Recupera uma lista de {@link Product}  de determinada empresa do banco de dados
     * @param branchId Id da filial
     * @param callback o {@link br.com.doubletouch.vendasup.data.database.ProductDatabase.ProductListCallback} que notificara os clients.
     */
    void list(final Integer branchId , final ProductListCallback callback);

}
