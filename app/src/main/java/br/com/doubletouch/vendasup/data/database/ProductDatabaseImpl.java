package br.com.doubletouch.vendasup.data.database;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
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

        List<Product> products = productPersistence.getAll();
        callback.onProductListLoaded(products);
    }
}
