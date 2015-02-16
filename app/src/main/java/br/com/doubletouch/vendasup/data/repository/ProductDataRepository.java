package br.com.doubletouch.vendasup.data.repository;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.exception.ProductNotFoundException;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;
import br.com.doubletouch.vendasup.data.repository.datasource.ProductDataStore;
import br.com.doubletouch.vendasup.data.repository.datasource.ProductDataStoreFactory;
import br.com.doubletouch.vendasup.domain.repository.ProductRepository;

/**
 *
 * Created by LADAIR on 12/02/2015.
 */
public class ProductDataRepository implements ProductRepository {

    private final ProductDataStoreFactory productDataStoreFactory;


    private static ProductDataRepository INSTANCE;

    public static synchronized ProductDataRepository getInstace(ProductDataStoreFactory dataStoreFactory) {
        if(INSTANCE == null){
            INSTANCE = new ProductDataRepository(dataStoreFactory);
        }

        return INSTANCE;
    }

    public ProductDataRepository(ProductDataStoreFactory productDataStoreFactory) {
        if(productDataStoreFactory == null){
            throw  new IllegalArgumentException("Não é permitido parametros nulos no construtor.");
        }

        this.productDataStoreFactory = productDataStoreFactory;
    }

    @Override
    public void getProductList(Integer branchId, final ProductListCallback productListCallback) {
        final ProductDataStore productDataStore = this.productDataStoreFactory.create();
        productDataStore.getProductList(branchId, new ProductDataStore.ProductListCallback() {
            @Override
            public void onProductListLoaded(Collection<Product> productsCollection) {
                productListCallback.onProductListLoaded(productsCollection);
            }

            @Override
            public void onError(Exception exception) {
                productListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getProductById(int productId, final ProductDetailsCallback productDetailsCallback) {
        final ProductDataStore productDataStore = this.productDataStoreFactory.create();
        productDataStore.getProductDetails(productId, new ProductDataStore.ProductDetailsCallback() {
            @Override
            public void onProductLoaded(Product product) {
                if(product != null){
                    productDetailsCallback.onProductLoaded(product);
                } else {
                    productDetailsCallback.onError(new RepositoryErrorBundle(new ProductNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                productDetailsCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
