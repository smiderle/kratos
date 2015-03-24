package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.ProductDAO;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.exception.ProductNotFoundException;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 *
 * Created by LADAIR on 12/02/2015.
 */
public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    public ProductServiceImpl() {
        this.productDAO = new ProductDAO();
    }

    @Override
    public void getProductList( final ProductListCallback productListCallback) {

        try{
            List<Product> products = productDAO.getAll(VendasUp.getUsuarioLogado().getBranchID());
            productListCallback.onProductListLoaded(products);
        }catch ( Exception e){
            productListCallback.onError(new RepositoryErrorBundle(e));
        }
    }

    @Override
    public void getProductListByFilter(String description, String productId,  final ProductListFilterCallback productListFilterCallback) {

        try {
            List<Product> products = productDAO.getByDescriptionOrProductId(description, productId, VendasUp.getUsuarioLogado().getBranchID());
            productListFilterCallback.onProductListFilterLoaded(products);
        }catch ( Exception e){
            productListFilterCallback.onError(new RepositoryErrorBundle(e));
        }

    }

    @Override
    public void getProductById(int productId, final ProductDetailsCallback productDetailsCallback) {
        try {
            Product product = productDAO.get(productId);
            if(product != null){
                productDetailsCallback.onProductLoaded(product);
            } else {
                productDetailsCallback.onError(new RepositoryErrorBundle(new ProductNotFoundException()));
            }
        } catch (Exception e) {
            productDetailsCallback.onError(new RepositoryErrorBundle(e));
        }

    }

    @Override
    public void saveOrUpdateSynchronous(List<Product> produtos) {

        productDAO.insert(produtos);

    }


    @Override
    public void save(Product product,final ProductSaveCallback productSaveCallback) {

        try{
            productDAO.insert(product);
            productSaveCallback.onProductSaved(product);
        }catch (Exception e) {
            productSaveCallback.onError(new RepositoryErrorBundle(e));

        }
    }
}
