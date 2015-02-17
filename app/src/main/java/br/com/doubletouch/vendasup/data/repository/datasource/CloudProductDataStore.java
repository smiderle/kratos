package br.com.doubletouch.vendasup.data.repository.datasource;

/**
 * {@link br.com.doubletouch.vendasup.data.repository.datasource.ProductDataStore implementação baseado nas conexões da api (Cloud)}
 * Created by LADAIR on 12/02/2015.
 */
public class CloudProductDataStore implements ProductDataStore {

    @Override
    public void getProductList(Integer branchId, ProductListCallback productListCallback) {

    }

    @Override
    public void getProductListFilter(String description, String productId, Integer branchId, ProductListFilterCallback productListFilterCallback) {

    }


    @Override
    public void getProductDetails(Integer productId, ProductDetailsCallback productDetailsCallback) {

    }
}
