package br.com.doubletouch.vendasup.data.repository.datasource;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.database.ProductDatabase;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * {@link br.com.doubletouch.vendasup.data.repository.datasource.ProductDataStore implementação para acesso ao banco de dados
 * Created by LADAIR on 12/02/2015.
 */
public class DatabaseProductDataStore implements ProductDataStore {

    ProductDatabase productDatabase;

    public DatabaseProductDataStore(ProductDatabase productDatabase) {
        this.productDatabase = productDatabase;
    }

    @Override
    public void getProductList(Integer branchId, final ProductListCallback productListCallback) {
        this.productDatabase.list(branchId, new ProductDatabase.ProductListCallback() {
            @Override
            public void onProductListLoaded(Collection<Product> productsCollection) {
                productListCallback.onProductListLoaded(productsCollection);
            }

            @Override
            public void onError(Exception exception) {
                productListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getProductDetails(Integer productId, final ProductDetailsCallback productDetailsCallback) {
        this.productDatabase.get(productId, new ProductDatabase.ProductDetailsCallback() {
            @Override
            public void onProductLoaded(Product product) {
                productDetailsCallback.onProductLoaded(product);
            }

            @Override
            public void onError(Exception exception) {
                productDetailsCallback.onError(exception);
            }
        });
    }
}
