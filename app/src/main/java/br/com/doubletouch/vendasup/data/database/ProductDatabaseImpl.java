package br.com.doubletouch.vendasup.data.database;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 *
 * Created by LADAIR on 12/02/2015.
 */
public class ProductDatabaseImpl implements ProductDatabase {

    private final ProductPersistence productPersistence;

    public ProductDatabaseImpl(ProductPersistence productPersistence) {
        this.productPersistence = productPersistence;
    }

    @Override
    public synchronized void get(Integer productId, ProductDetailsCallback callback) {
        Product product = productPersistence.get(productId);
        callback.onProductLoaded(product);
    }

    @Override
    public synchronized void list(Integer branchId, ProductListCallback callback) {

        List<Product> products = productPersistence.getAll(branchId);
        callback.onProductListLoaded(products);
    }

    @Override
    public synchronized void listByFilter(String description, String productId,Integer branchId, ProductListFilterCallback callback) {
        List<Product> products = productPersistence.getByDescriptionOrProductId(description, productId, branchId);
        callback.onProductListFilterLoaded(products);
    }
}
