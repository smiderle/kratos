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
            List<Product> products = productDAO.getAll(VendasUp.getBranchOffice().getBranchOfficeID());
            productListCallback.onProductListLoaded(products);
        }catch ( Exception e){
            productListCallback.onError(new RepositoryErrorBundle(e));
        }
    }

    @Override
    public void getProductListByFilter(String description, String productId,  final ProductListFilterCallback productListFilterCallback) {

        try {
            List<Product> products = productDAO.getByDescriptionOrProductId(description, productId, VendasUp.getBranchOffice().getBranchOfficeID());
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

    @Override
    public List<Product> getAllSyncPendenteAtualizados(Integer branchId) {

        return productDAO.getAllSyncPendenteAtualizados(branchId);

    }

    @Override
    public List<Product> getAllSyncPendenteNovos(Integer branchId) {

        return productDAO.getAllSyncPendenteNovos(branchId);

    }



    @Override
    public Integer getNextId() {

        Integer max = productDAO.max();

        if(max == null){
            max = 1;
        }

        max += 1000;

        return max;

    }


    /**
     *Retorna o menor valor, negativo, dos ids, isso porque os pedidos serão salvos com id negativo, para não correr risco de já existir um id igual.
     * O Código do pedido será salvo com valor negativo, pois pode ser que já exista um outro pedido com o mesmo código, quando for feito o update com o retorno do novo id.
     * @return
     */
    @Override
    public Integer getLessNegative() {

        Integer min = productDAO.min();

        if(min <= 0){

            min -= 1;

        } else {
            min = -1;
        }

        return min;
    }

    @Override
    public void updateByIdMobile(List<Product> products) {

        OrderItemService orderService = new OrderItemServiceImpl();


        for(Product product : products) {

            orderService.updateProduct( product.getIdMobile(), product.getID() );

            product.setSyncPending(false);
            productDAO.updateByIdMobile(product);

        }
    }

    @Override
    public Product get(Integer id) {
        return productDAO.get(id);
    }
}
